package com.lengjiye.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpClientHolder {
    private var client: OkHttpClient? = null
    private var cookieJarImpl: CookieJarImpl? = null

    companion object {
        var singleton = Instance.holder
    }

    private object Instance {
        val holder = OkHttpClientHolder()
    }

    fun getHttpClient(): OkHttpClient {
        if (null == client) {
            client = createClient()
        }
        return client as OkHttpClient
    }

    private fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(SignInterceptor())
            .cookieJar(getCookieJarImpl())
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    fun getCookieJarImpl(): CookieJarImpl {
        if (cookieJarImpl == null) {
            cookieJarImpl = CookieJarImpl(PersistentCookieStore())
        }
        return cookieJarImpl as CookieJarImpl
    }

}