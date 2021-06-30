package com.lengjiye.code.main.serve

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.HotKey
import com.lengjiye.network.BaseHttpResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface MainServe {

    @GET(ServerApi.HOT_KEY_LIST)
    fun getHotKeyList(): Flow<BaseHttpResult<List<HotKey>>>
}