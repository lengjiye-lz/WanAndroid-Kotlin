package com.lengjiye.code.search.serve

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.network.BaseHttpResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @Author: lz
 * @Date: 2020-03-03
 * @Description:
 */
interface SearchServe {

    @POST(ServerApi.ARTICLE_QUERY)
    @FormUrlEncoded
    fun search(@Path("page") page: Int, @Field("k") key: String): Observable<BaseHttpResult<ArticleBean>>
}