package com.kepler.component.update.entity

sealed interface ApkDownloadStatus {

    object NeedDownload : ApkDownloadStatus

    data class Downloading(val percent: Float) : ApkDownloadStatus

    data class DownloadFailed(val percent: Float) : ApkDownloadStatus

    data class DownloadSuccess(val path: String) : ApkDownloadStatus
}