package com.kepler.components.http

import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.Koin
import son.ysy.architecture.getter.VersionGetter
import son.ysy.architecture.http.interceptor.HttpInterceptor

internal class CommonParamInterceptor(koin: Koin) : HttpInterceptor(0) {
    object Config {
        const val KEY_USER_AGENT = "user-agent"
        const val KEY_DEVICE_TYPE = "deviceType"
        const val KEY_APP_NAME = "appName"
        const val KEY_VERSION = "version"
    }

    private val versionGetter: VersionGetter by koin.inject()

    override fun intercept(chain: Interceptor.Chain): Response {

        val oldRequest = chain.request()

        val urlBuilder = oldRequest
            .url
            .newBuilder()
            .addQueryParameter(Config.KEY_DEVICE_TYPE, "0")
            .addQueryParameter(Config.KEY_APP_NAME, "dehecircle")
            .addQueryParameter(Config.KEY_VERSION, versionGetter.getVersionName())

        val request = oldRequest
            .newBuilder()
            .url(urlBuilder.build())
            .addHeader(Config.KEY_USER_AGENT, "Android")
            .build()

        return chain.proceed(request)
    }
}