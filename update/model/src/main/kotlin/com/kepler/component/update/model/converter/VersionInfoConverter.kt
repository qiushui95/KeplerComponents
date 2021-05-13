package com.kepler.component.update.model.converter

import com.kepler.component.update.entity.VersionInfo
import com.kepler.component.update.model.room.entity.VersionInfoEntity

internal fun VersionInfoEntity.convert(): VersionInfo {
    return VersionInfo(updateId, targetVersion, url, isForce)
}