package com.lengjiye.code.search.serve

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.networkscope.BaseHttpResult
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
    suspend fun search(@Path("page") page: Int, @Field("k") key: String): BaseHttpResult<ArticleBean>
}