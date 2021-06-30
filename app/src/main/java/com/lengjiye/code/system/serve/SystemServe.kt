package com.lengjiye.code.system.serve

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.network.BaseHttpResult
import com.lengjiye.room.entity.SystemTreeEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SystemServe {

    @GET(ServerApi.TREE)
    fun getTree(): Flow<BaseHttpResult<List<SystemTreeEntity>>>

    @GET(ServerApi.TREE_ARTICLE)
    fun getTreeArticleList(@Path("page") page: Int, @Query("cid") cid: Int): Flow<BaseHttpResult<ArticleBean>>
}