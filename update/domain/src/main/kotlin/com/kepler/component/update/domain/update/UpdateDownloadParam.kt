package com.kepler.component.update.domain.update

import com.kepler.component.update.domain.param.VersionId

sealed class UpdateDownloadParam {

    data class Downloading(
        val id: VersionId,
        val percent: Float
    ) : UpdateDownloadParam()

    data class Failed(val id: VersionId, val msg: String) : UpdateDownloadParam()

    data class Success(val id: VersionId, val path: String) : UpdateDownloadParam()
}
