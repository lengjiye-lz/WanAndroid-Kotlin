package com.lengjiye.code.system.serve

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.network.BaseHttpResult
import com.lengjiye.room.entity.SystemTreeEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SystemServe {

    @GET(ServerApi.TREE)
    fun getTree(): Observable<BaseHttpResult<List<SystemTreeEntity>>>

    @GET(ServerApi.TREE_ARTICLE)
    fun getTreeArticleList(@Path("page") page: Int, @Query("cid") cid: Int): Observable<BaseHttpResult<ArticleBean>>
}