package com.lengjiye.code.me.service

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.bean.CoinList
import com.lengjiye.code.me.bean.Rank
import com.lengjiye.code.me.bean.RankTable
import com.lengjiye.code.me.bean.Website
import com.lengjiye.network.BaseHttpResult
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
interface MeService {

    @GET(ServerApi.COIN_RANK)
    fun getCoinRank(@Path("page") page: Int): Observable<BaseHttpResult<RankTable>>

    @GET(ServerApi.COIN_USER_INFO)
    fun getCoinUserInfo(): Observable<BaseHttpResult<Rank>>

    @GET(ServerApi.COIN_USER_INFO_LIST)
    fun getCoinUserInfoList(@Path("page") page: Int): Observable<BaseHttpResult<CoinList>>

    // 收藏文章列表
    @GET(ServerApi.COLLECT_ARTICLE_LIST)
    fun getCollectArticleList(@Path("page") page: Int): Observable<BaseHttpResult<ArticleBean>>

    // 收藏文章
    @POST(ServerApi.COLLECT_ADD_ARTICLE)
    fun collectAddArticle(@Path("id") id: Int): Observable<BaseHttpResult<String>>

    // 收藏站外文章
    @POST(ServerApi.COLLECT_ADD_OTHER_ARTICLE)
    @FormUrlEncoded
    fun collectAddOtherArticle(@Field("title") title: String, @Field("author") author: String, @Field("link") link: String): Observable<BaseHttpResult<String>>

    // 取消收藏
    @POST(ServerApi.UN_COLLECT_ARTICLE)
    fun unCollectArticle(@Path("id") id: Int): Observable<BaseHttpResult<String>>

    // 取消我的收藏
    @POST(ServerApi.UN_COLLECT_ARTICLE)
    @FormUrlEncoded
    fun unMyCollectArticle(@Path("id") id: Int, @Field("originId") originId: Int): Observable<BaseHttpResult<String>>

    // 收藏的网站列表
    @GET(ServerApi.COLLECT_WEBSITE_LIS)
    fun collectWebsiteLis(): Observable<BaseHttpResult<List<Website>>>

    // 收藏网站
    @POST(ServerApi.COLLECT_ADD_WEBSITE)
    @FormUrlEncoded
    fun collectAddWebsite(@Field("name") name: String, @Field("link") link: String): Observable<BaseHttpResult<String>>

    // 编辑网站
    @POST(ServerApi.COLLECT_UPDATE_WEBSITE)
    @FormUrlEncoded
    fun collectUpdateWebsite(@Field("id") id: Int, @Field("name") name: String, @Field("link") link: String): Observable<BaseHttpResult<String>>

    // 删除网站
    @POST(ServerApi.COLLECT_DELETE_WEBSITE)
    @FormUrlEncoded
    fun collectDeleteWebsite(@Field("id") id: Int): Observable<BaseHttpResult<String>>
}