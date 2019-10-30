package com.lengjiye.codelibrarykotlin.home.service

import com.lengjiye.codelibrarykotlin.constant.ServerConstants
import com.lengjiye.codelibrarykotlin.home.bean.ArticleBean
import com.lengjiye.codelibrarykotlin.home.bean.BannerBean
import com.lengjiye.codelibrarykotlin.home.bean.HomeBean
import com.lengjiye.network.BaseHttpResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {

    @GET(ServerConstants.ARTICLE)
    fun getArticle(@Path("page") page: Int): Observable<BaseHttpResult<ArticleBean>>

    @GET(ServerConstants.ARTICLE_TOP)
    fun getArticleTop(): Observable<BaseHttpResult<List<HomeBean>>>

    @GET(ServerConstants.BANNER)
    fun getBanner(): Observable<BaseHttpResult<List<BannerBean>>>

}