package com.lengjiye.code.main.service

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.bean.BannerBean
import com.lengjiye.code.home.bean.HomeBean
import com.lengjiye.code.home.bean.Hotkey
import com.lengjiye.network.BaseHttpResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MainService {

    @GET(ServerApi.HOTKEY_LIST)
    fun getHotkeyList(): Observable<BaseHttpResult<List<Hotkey>>>
}