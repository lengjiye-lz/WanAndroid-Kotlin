package com.lengjiye.code.main.serve

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.HotKey
import com.lengjiye.network.BaseHttpResult
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface MainServe {

    @GET(ServerApi.HOT_KEY_LIST)
    fun getHotKeyList(): Observable<BaseHttpResult<List<HotKey>>>

    @GET(ServerApi.HOT_KEY_LIST)
    fun getHotKeyList1(): Call<BaseHttpResult<List<HotKey>>>
}