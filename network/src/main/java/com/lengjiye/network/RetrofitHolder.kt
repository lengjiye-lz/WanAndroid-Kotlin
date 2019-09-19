package com.lengjiye.network

import com.google.gson.GsonBuilder
import com.lengjiye.base.application.MasterApplication
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHolder {
    private var retrofit: Retrofit? = null

    companion object {
        val singleton = Instance.holder
    }

    private object Instance {
        val holder = RetrofitHolder()
    }

    fun getRetrofit(): Retrofit {
        if (null == retrofit) {
            retrofit = createRetrofit()
        }
        return retrofit as Retrofit
    }

    private fun createRetrofit(): Retrofit {
        val url = MasterApplication.getInstance().baseUrl()
        return Retrofit.Builder().baseUrl(url)
            .client(OkHttpClientHolder.singleton.getHttpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}