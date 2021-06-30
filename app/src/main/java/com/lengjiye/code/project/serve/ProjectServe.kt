package com.lengjiye.code.project.serve

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.network.BaseHttpResult
import com.lengjiye.room.entity.ProjectTreeEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
interface ProjectServe {

    @GET(ServerApi.PROJECT_TREE)
    fun getProjectTree(): Flow<BaseHttpResult<List<ProjectTreeEntity>>>

    @GET(ServerApi.PROJECT_TREE_ARTICLE)
    fun getProjectArticle(@Path("page") page: Int, @Query("cid") cid: Int): Flow<BaseHttpResult<ArticleBean>>
}