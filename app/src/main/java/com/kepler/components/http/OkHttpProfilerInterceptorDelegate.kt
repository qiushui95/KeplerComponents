package com.kepler.components.http

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.Interceptor
import son.ysy.architecture.http.interceptor.HttpInterceptor

internal class OkHttpProfilerInterceptorDelegate : HttpInterceptor(Int.MAX_VALUE),
    Interceptor by OkHttpProfilerInterceptor() {
}