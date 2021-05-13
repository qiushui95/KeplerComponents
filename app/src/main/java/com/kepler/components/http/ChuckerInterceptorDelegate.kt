package com.kepler.components.http

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import son.ysy.architecture.http.interceptor.HttpInterceptor

internal class ChuckerInterceptorDelegate(
    context: Context
) : HttpInterceptor(Int.MAX_VALUE), Interceptor by ChuckerInterceptor.Builder(context).build()