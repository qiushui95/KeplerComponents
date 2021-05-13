package com.kepler.component.update.entity

data class UpdateInfo(
    val id: String,
    val version: String,
    val url: String,
    val isForce: Boolean,
    val status: UpdateStatus
)
