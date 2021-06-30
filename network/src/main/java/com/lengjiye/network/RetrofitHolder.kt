package com.lengjiye.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHolder {

    private val gson: Gson by lazy {
        val builder = GsonBuilder()
        builder.create()
    }

    fun createRetrofit(url:String): Retrofit {
        return Retrofit.Builder().baseUrl(url)
            .client(OkHttpClientHolder.createClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .build()
    }
}