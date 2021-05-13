package com.kepler.components

import son.ysy.architecture.getter.VersionGetter

internal class VersionGetterImpl : VersionGetter {
//    override fun getVersionName(): String = BuildConfig.VERSION_NAME
    override fun getVersionName(): String = "3.2.0"

    override fun getVersionCode(): Int = BuildConfig.VERSION_CODE

    override fun getHotFixCode(): Int = 0
}