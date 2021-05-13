package com.kepler.component.update.model.converter

import com.kepler.component.update.entity.ApkDownloadStatus
import com.kepler.component.update.model.room.entity.DownloadStatusEntity

internal fun DownloadStatusEntity?.convert(): ApkDownloadStatus {
    return when {
        this == null || percent == DownloadStatusEntity.NOT_START_PERCENT -> ApkDownloadStatus.NeedDownload
        isFailed -> ApkDownloadStatus.DownloadFailed(percent)
        path != null -> ApkDownloadStatus.DownloadSuccess(path)
        else -> ApkDownloadStatus.Downloading(percent)
    }
}