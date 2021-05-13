package com.kepler.component.update.model.repository

import android.util.Log
import com.kepler.component.update.entity.VersionInfo
import com.kepler.component.update.entity.ApkDownloadStatus
import com.kepler.component.update.model.api.ResponseUpdateLog
import com.kepler.component.update.model.api.UpdateApi
import com.kepler.component.update.model.converter.convert
import com.kepler.component.update.model.room.UpdateDao
import com.kepler.component.update.model.room.entity.DownloadStatusEntity
import com.kepler.component.update.model.room.entity.UpdateLogEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.koin.core.Koin
import son.ysy.architecture.getter.TokenGetter
import son.ysy.architecture.getter.VersionGetter
import son.ysy.architecture.model.repository.BaseRepositoryImpl

internal class UpdateRepositoryImpl(
    koin: Koin
) : BaseRepositoryImpl(), UpdateRepository,
    TokenGetter by koin.get(),
    VersionGetter by koin.get() {

    private val updateApi: UpdateApi by koin.inject()

    private val updateDao: UpdateDao by koin.inject()

    override suspend fun checkNewestVersion(isCallByUser: Boolean): VersionInfo? {
        Log.e("==========","checkNewestVersion")

        val token = getUserToken()

        val updateContainer = updateApi.loadUpdateInfo(token)

        val needUpdate = updateContainer.needUpdate == "1"

        val versionInfo = updateContainer.versionInfo

        if (!needUpdate || versionInfo == null) {
            return null
        }

        val (id, version, isForce, url) = versionInfo

        val currentVersion = getVersionName()

        updateDao.insertOrUpdateOrDelete(
            id,
            version,
            url,
            isForce,
            currentVersion,
            isCallByUser
        )

        return VersionInfo(id, version, url, isForce)
    }

    private suspend fun getDownloadStatusById(id: String): DownloadStatusEntity {
        return updateDao.getDownloadStatusById(id)
            ?: DownloadStatusEntity(id, DownloadStatusEntity.NOT_START_PERCENT, false, null)
    }

    override suspend fun updateDownloadPercent(id: String, percent: Float) {
        val status = getDownloadStatusById(id).copy(
            percent = percent,
            isFailed = false,
            path = null,
        )

        updateDao.updateDownloadStatus(status)
    }

    override suspend fun updateDownloadFailed(id: String, msg: String) {
        val status = getDownloadStatusById(id).copy(
            isFailed = true,
            path = null,
        )
        val info = updateDao.getUpdateInfoById(id) ?: return

        updateDao.updateDownloadStatus(status)

        updateDao.insertUpdateLog(
            listOf(
                UpdateLogEntity(
                    updateId = id,
                    desc = "下载失败,下载进度:${status.percent}%,原因:$msg",
                    currentVersion = info.currentVersion,
                    targetVersion = info.targetVersion,
                    status = "0"
                )
            )
        )

        uploadUpdateLog()
    }

    override suspend fun updateDownloadSuccess(id: String, path: String) {
        val status = getDownloadStatusById(id).copy(
            isFailed = false,
            path = path,
        )

        updateDao.updateDownloadStatus(status)
    }

    override suspend fun newIgnoreRecord(id: String, targetVersion: String) {

        updateDao.insertIgnoreRecord(id, System.currentTimeMillis())

        val currentVersion = getVersionName()

        updateDao.insertUpdateLog(
            listOf(
                UpdateLogEntity(
                    updateId = id,
                    desc = "下载失败",
                    currentVersion = currentVersion,
                    targetVersion = targetVersion,
                    status = "下次更新"
                )
            )
        )

        uploadUpdateLog()

    }

    override suspend fun newInstallRecord(id: String,targetVersion: String) {
        updateDao.insertInstallRecord(id,getVersionName(),targetVersion, System.currentTimeMillis())
    }

    override fun getAvailableVersionInfoFlow(start: Long, end: Long): Flow<VersionInfo?> {
        val currentVersion = getVersionName()

        return updateDao.getAutoVersionInfoFlow(start, end, currentVersion)
            .distinctUntilChanged()
            .map {
                it?.convert()
            }
    }

    override fun getDownloadStatusFlow(id: String): Flow<ApkDownloadStatus> {
        return updateDao.getDownloadStatusFlow(id)
            .map {
                it.convert()
            }
    }

    override suspend fun newInstallLog() {
        val currentVersion = getVersionName()

        val list = updateDao.getNotUploadInstallLog(currentVersion)

        val logList = list.map {
            val success = currentVersion == it.targetVersion
            UpdateLogEntity(
                updateId = it.updateId,
                desc = if (success)
                    "成功"
                else "安装失败",
                status = if (success)
                    "1"
                else "0",
                currentVersion = it.currentVersion,
                targetVersion = it.targetVersion
            )
        }

        updateDao.insertUpdateLog(logList)

        updateDao.updateInstallUploadStatus(list.map { it.id })

        uploadUpdateLog()
    }

    override suspend fun uploadUpdateLog() {

        Log.e("============","uploadUpdateLog")

        val ids = coroutineScope {
            updateDao.getAllUpdateLog()
                .map {
                    val (id, updateId, desc, currentVersion, targetVersion, status) = it
                    async(Dispatchers.IO) {
                        try {
                            updateApi.uploadUpdateLog(
                                ResponseUpdateLog(
                                    updateId,
                                    desc,
                                    currentVersion,
                                    targetVersion,
                                    status
                                )
                            )
                            id
                        } catch (e: Exception) {
                            null
                        }
                    }
                }.mapNotNull {
                    it.await()
                }
        }

        return updateDao.deleteUploadLogInId(ids)
    }
}