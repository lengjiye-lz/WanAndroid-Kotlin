package com.lengjiye.network

import com.lengjiye.network.cookie.CookieJarImpl
import com.lengjiye.network.cookie.PersistentCookieStore
import com.lengjiye.network.interceptor.SignInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkHttpClientHolder {
    private var cookieJarImpl: CookieJarImpl? = null

    fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(SignInterceptor())
            .cookieJar(getCookieJarImpl())
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    private fun getCookieJarImpl(): CookieJarImpl {
        if (cookieJarImpl == null) {
            cookieJarImpl = CookieJarImpl(PersistentCookieStore())
        }
        return cookieJarImpl as CookieJarImpl
    }

}