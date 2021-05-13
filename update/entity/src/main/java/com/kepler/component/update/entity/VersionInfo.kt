package com.kepler.component.update.entity

data class VersionInfo(
    val id: String,
    val targetVersion: String,
    val url: String,
    val isForce: Boolean
)
