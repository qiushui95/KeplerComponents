package com.kepler.component.update.model.repository

interface TokenGetter {

    suspend fun getCurrentToken(): String?
}