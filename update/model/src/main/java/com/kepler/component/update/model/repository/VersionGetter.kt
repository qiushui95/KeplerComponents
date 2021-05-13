package com.kepler.component.update.model.repository

interface VersionGetter {

    suspend fun getCurrentVersion(): String
}