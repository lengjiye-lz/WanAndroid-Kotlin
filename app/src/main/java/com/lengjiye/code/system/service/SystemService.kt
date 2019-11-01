package com.lengjiye.code.system.service

import com.lengjiye.code.constant.ServerConstants
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.network.BaseHttpResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SystemService {

    @GET(ServerConstants.TREE)
    fun getTree(): Observable<BaseHttpResult<List<TreeBean>>>

    @GET(ServerConstants.TREE_ARTICLE_LIST)
    fun getTreeArticleList(@Path("page") page: Int, @Query("cid") cid: Int): Observable<BaseHttpResult<ArticleBean>>
}