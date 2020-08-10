package com.lengjiye.code.networkscope

import com.lengjiye.code.interceptor.SignInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkHttpClientHolder {

    fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(SignInterceptor())
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }
}