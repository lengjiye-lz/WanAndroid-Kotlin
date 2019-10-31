package com.lengjiye.code.home.service

import com.lengjiye.code.constant.ServerConstants
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.bean.BannerBean
import com.lengjiye.code.home.bean.HomeBean
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