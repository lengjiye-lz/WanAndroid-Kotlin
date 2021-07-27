package com.lengjiye.code.me.serve

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.bean.CoinList
import com.lengjiye.code.me.bean.Rank
import com.lengjiye.code.me.bean.RankTable
import com.lengjiye.code.me.bean.Website
import com.lengjiye.network.BaseHttpResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
interface MeServe {

    @GET(ServerApi.COIN_RANK)
    fun getCoinRank(@Path("page") page: Int): Flow<BaseHttpResult<RankTable>>

    @GET(ServerApi.COIN_USER_INFO)
    fun getCoinUserInfo(): Flow<BaseHttpResult<Rank>>

    @GET(ServerApi.COIN_USER_INFO_LIST)
    fun getCoinUserInfoList(@Path("page") page: Int): Flow<BaseHttpResult<CoinList>>

    // 收藏文章列表
    @GET(ServerApi.COLLECT_ARTICLE_LIST)
    fun getCollectArticleList(@Path("page") page: Int): Flow<BaseHttpResult<ArticleBean>>

    // 收藏文章
    @POST(ServerApi.COLLECT_ADD_ARTICLE)
    fun collectAddArticle(@Field("id") id: Int): Flow<BaseHttpResult<String>>

    // 收藏站外文章
    @POST(ServerApi.COLLECT_ADD_OTHER_ARTICLE)
    @FormUrlEncoded
    fun collectAddOtherArticle(@Field("title") title: String, @Field("author") author: String, @Field("link") link: String): Flow<BaseHttpResult<String>>

    // 取消收藏
    @POST(ServerApi.UN_COLLECT_ORIGINID_ARTICLE)
    fun unCollectArticle(@Path("id") id: Int): Flow<BaseHttpResult<String>>

    // 取消我的收藏
    @POST(ServerApi.UN_COLLECT_ARTICLE)
    @FormUrlEncoded
    fun unMyCollectArticle(@Path("id") id: Int, @Field("originId") originId: Int): Flow<BaseHttpResult<String>>

    // 收藏的网站列表
    @GET(ServerApi.COLLECT_WEBSITE_LIS)
    fun collectWebsiteLis(): Flow<BaseHttpResult<List<Website>>>

    // 收藏网站
    @POST(ServerApi.COLLECT_ADD_WEBSITE)
    @FormUrlEncoded
    fun collectAddWebsite(@Field("name") name: String, @Field("link") link: String): Flow<BaseHttpResult<String>>

    // 编辑网站
    @POST(ServerApi.COLLECT_UPDATE_WEBSITE)
    @FormUrlEncoded
    fun collectUpdateWebsite(@Field("id") id: Int, @Field("name") name: String, @Field("link") link: String): Flow<BaseHttpResult<String>>

    // 删除网站
    @POST(ServerApi.COLLECT_DELETE_WEBSITE)
    @FormUrlEncoded
    fun collectDeleteWebsite(@Field("id") id: Int): Flow<BaseHttpResult<String>>
}