package com.kepler.components

import son.ysy.architecture.getter.TokenGetter

internal class TokenGetterImpl : TokenGetter {
    override suspend fun getUserToken(): String? {
        return null
    }
}