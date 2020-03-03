package com.lengjiye.code.main.service

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.HotKey
import com.lengjiye.network.BaseHttpResult
import io.reactivex.Observable
import retrofit2.http.GET

interface MainService {

    @GET(ServerApi.HOT_KEY_LIST)
    fun getHotKeyList(): Observable<BaseHttpResult<List<HotKey>>>
}