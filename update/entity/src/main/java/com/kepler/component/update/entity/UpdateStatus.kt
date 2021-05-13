package com.kepler.component.update.entity

sealed interface UpdateStatus {

    object NeedDownload : UpdateStatus

    data class Downloading(val percent: Float) : UpdateStatus

    data class DownloadFailed(val percent: Float) : UpdateStatus

    data class DownloadSuccess(val path: String) : UpdateStatus
}