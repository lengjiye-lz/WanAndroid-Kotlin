package com.lengjiye.code.me.service

import com.lengjiye.code.constant.ServerConstants
import com.lengjiye.code.me.bean.CoinList
import com.lengjiye.code.me.bean.Rank
import com.lengjiye.code.me.bean.RankTable
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
interface MeService {

    @GET(ServerConstants.COIN_RANK)
    fun getCoinRank(@Path("page") page: Int): Observable<BaseHttpResult<RankTable>>

    @GET(ServerConstants.COIN_USERINFO)
    fun getCoinUserInfo(): Observable<BaseHttpResult<Rank>>

    @GET(ServerConstants.COIN_USERINFO_LIST)
    fun getCoinUserInfoList(@Path("page") page: Int): Observable<BaseHttpResult<CoinList>>

}