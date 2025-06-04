package com.cesar.data.core

import okhttp3.Interceptor
import okhttp3.Response

class BasicInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .build()

        return chain.proceed(newRequest)
    }
}