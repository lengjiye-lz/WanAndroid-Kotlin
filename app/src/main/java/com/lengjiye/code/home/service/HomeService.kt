package com.lengjiye.code.home.service

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.bean.BannerBean
import com.lengjiye.code.home.bean.HomeBean
import com.lengjiye.network.BaseHttpResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {

    @GET(ServerApi.ARTICLE)
    fun getArticle(@Path("page") page: Int): Observable<BaseHttpResult<ArticleBean>>

    @GET(ServerApi.ARTICLE_TOP)
    fun getArticleTop(): Observable<BaseHttpResult<List<HomeBean>>>

    @GET(ServerApi.BANNER)
    fun getBanner(): Observable<BaseHttpResult<List<BannerBean>>>
}