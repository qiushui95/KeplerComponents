package com.kepler.component.update.model.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ResponseUpdateContainer(
    @Json(name = "updateOrNot")
    val needUpdate: String,
    @Json(name = "versionInfo")
    val versionInfo: ResponseUpdateInfo?
)