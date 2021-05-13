package com.kepler.component.update.model.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ResponseUpdateInfo(
    @Json(name = "id")
    val id: String,
    @Json(name = "version")
    val version: String,
    @Json(name = "status")
    val isForce: Boolean,
    @Json(name = "url")
    val url: String,
    @Json(name = "updateTime")
    val time: String
)