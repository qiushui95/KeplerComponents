package com.kepler.component.update.model.repository

import com.kepler.component.update.model.api.UpdateApi
import com.kepler.component.update.model.room.UpdateDao
import org.koin.core.Koin
import son.ysy.lib.model.repository.BaseRepositoryImpl

internal class UpdateRepositoryImpl(
    koin: Koin
) : BaseRepositoryImpl(), UpdateRepository,
    TokenGetter by koin.get(),
    VersionGetter by koin.get() {

    private val updateApi: UpdateApi by koin.inject()

    private val updateDao: UpdateDao by koin.inject()

    override suspend fun loadUpdateInfo(): Unit? {
        val token = getCurrentToken()

        val (needUpdate, versionInfo) = updateApi.loadUpdateInfo(token)

        if (!needUpdate || versionInfo == null) {
            return null
        }

        val (id, targetVersion, isForce, url, time) = versionInfo

        val currentVersion = getCurrentVersion()

        updateDao.insertOrUpdateOrDelete(
            id + time,
            targetVersion,
            url,
            isForce,
            currentVersion
        )

        return Unit
    }

}