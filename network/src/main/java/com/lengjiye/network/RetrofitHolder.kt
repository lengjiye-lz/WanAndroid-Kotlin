package com.lengjiye.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lengjiye.base.application.MasterApplication
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHolder {

    private val gson: Gson by lazy {
        val builder = GsonBuilder()
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        builder.create()
    }

    companion object {
        val singleton = Instance.holder
    }

    private object Instance {
        val holder = RetrofitHolder()
    }

    fun createRetrofit(): Retrofit {
        val url = MasterApplication.getInstance().baseUrl()
        return Retrofit.Builder().baseUrl(url)
            .client(OkHttpClientHolder.singleton.createClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}