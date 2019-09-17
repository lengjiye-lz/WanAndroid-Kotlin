package com.lengjiye.codelibrarykotlin.home.service

import com.lengjiye.codelibrarykotlin.constant.ServerConstants
import com.lengjiye.codelibrarykotlin.home.bean.Article
import com.lengjiye.codelibrarykotlin.home.bean.HomeBean
import com.lengjiye.network.BaseHttpResult
import io.reactivex.Observable
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeService {

    @POST()
    @FormUrlEncoded
    fun getsdc(): Observable<BaseHttpResult<HomeBean>>

    @GET(ServerConstants.ARTICLE_LIST)
    fun getArticleList(@Path("page") page: Int): Observable<BaseHttpResult<Article>>

}