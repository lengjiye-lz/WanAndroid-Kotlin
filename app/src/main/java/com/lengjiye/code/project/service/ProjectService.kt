package com.lengjiye.code.project.service

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.network.BaseHttpResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
interface ProjectService {

    @GET(ServerApi.PROJECT_TREE)
    fun getProjectTree(): Observable<BaseHttpResult<List<TreeBean>>>

    @GET(ServerApi.PROJECT_TREE_ARTICLE)
    fun getProjectArticle(@Path("page") page: Int, @Query("cid") cid: Int): Observable<BaseHttpResult<ArticleBean>>
}