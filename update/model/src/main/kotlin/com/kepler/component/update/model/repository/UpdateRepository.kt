package com.kepler.component.update.model.repository

import com.kepler.component.update.entity.ApkDownloadStatus
import com.kepler.component.update.entity.VersionInfo
import kotlinx.coroutines.flow.Flow

interface UpdateRepository {

    /**
     * 检查最新版本
     */
    suspend fun checkNewestVersion(isCallByUser: Boolean): VersionInfo?

    /**
     * 更新下载进度
     */
    suspend fun updateDownloadPercent(id: String, percent: Float)

    /**
     * 更新下载失败
     */
    suspend fun updateDownloadFailed(id: String, msg: String)

    /**
     * 更新下载成功
     */
    suspend fun updateDownloadSuccess(id: String, path: String)

    /**
     * 新增更新忽略记录
     */
    suspend fun newIgnoreRecord(
        id: String,
        targetVersion: String
    ): Unit?

    /**
     * 新增更新安装记录
     */
    suspend fun newInstallRecord(id: String,targetVersion: String)

    /**
     * 获取可用的更新信息flow
     */
    fun getAvailableVersionInfoFlow(start: Long, end: Long): Flow<VersionInfo?>

    /**
     * 获取下载状态
     */
    fun getDownloadStatusFlow(id: String): Flow<ApkDownloadStatus>

    /**
     * 生成更新安装记录
     */
    suspend fun newInstallLog()

    /**
     * 上传更新记录
     */
    suspend fun uploadUpdateLog()
}