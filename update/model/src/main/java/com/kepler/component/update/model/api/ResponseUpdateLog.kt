package com.kepler.component.update.model.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ResponseUpdateLog(
    @Json(name = "versionId")
    val id: String,
    @Json(name = "description")
    val desc: String,
    @Json(name = "currentVersion")
    val current: String,
    @Json(name = "targetVersion")
    val target: String,
    @Json(name = "status")
    val status: String
)