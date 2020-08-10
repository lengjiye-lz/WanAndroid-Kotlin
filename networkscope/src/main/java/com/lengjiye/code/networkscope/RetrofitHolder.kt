package com.lengjiye.code.networkscope

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHolder {

    fun createRetrofit(url: String): Retrofit {
        return Retrofit.Builder().baseUrl(url)
            .client(OkHttpClientHolder.createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}