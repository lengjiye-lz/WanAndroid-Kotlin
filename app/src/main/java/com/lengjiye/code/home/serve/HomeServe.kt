package com.lengjiye.code.home.serve

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.network.BaseHttpResult
import com.lengjiye.room.entity.HomeBannerEntity
import com.lengjiye.room.entity.HomeEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeServe {

    @GET(ServerApi.ARTICLE)
    fun getArticle(@Path("page") page: Int): Observable<BaseHttpResult<ArticleBean>>

    @GET(ServerApi.ARTICLE_TOP)
    fun getArticleTop(): Observable<BaseHttpResult<List<HomeEntity>>>

    @GET(ServerApi.BANNER)
    fun getBanner(): Observable<BaseHttpResult<List<HomeBannerEntity>>>
}