package com.kepler.component.update.model.repository

import com.kepler.component.update.entity.UpdateInfo

interface UpdateRepository {

    /**
     * 加载更新信息
     */
    suspend fun loadUpdateInfo(): Unit?

    /**
     * 更新下载进度
     */
    suspend fun updateDownloadPercent(id: String, percent: Float): Unit?

    /**
     * 更新下载失败
     */
    suspend fun updateDownloadFailed(id: String, msg: String): Unit?

    /**
     * 更新下载成功
     */
    suspend fun updateDownloadSuccess(id: String, path: String): Unit?

    /**
     * 新增更新忽略记录
     */
    suspend fun insertUpdateIgnoreRecord(
        id: String,
        targetVersion: String
    ): Unit?

    /**
     * 新增更新安装记录
     */
    suspend fun insertUpdateInstallRecord(id: String): Unit?

    /**
     * 获取更新信息flow
     */
    fun getUpdateInfoFlow(start: Long, end: Long): UpdateInfo?

    /**
     * 生成更新安装记录
     */
    suspend fun createInstallLog(): Unit?

    /**
     * 上传更新记录
     */
    suspend fun uploadUpdateLog(): Unit?
}