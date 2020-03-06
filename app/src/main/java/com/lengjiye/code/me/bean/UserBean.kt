package com.lengjiye.code.me.bean

import com.lengjiye.code.home.bean.ArticleBean

/**
 * @Author: lz
 * @Date: 2019-11-05
 */
data class UserBean(
    val admin: Boolean, // false
    val chapterTops: List<Any>,
    val collectIds: List<Any>,
    val email: String,
    val icon: String,
    val id: Int, // 1448
    val nickname: String, // lengjiye
    val password: String,
    val publicName: String, // lengjiye
    val token: String,
    val type: Int, // 0
    val username: String // lengjiye
)

data class Rank(
    val coinCount: Int, // 75
    val level: Int, // 1
    val rank: Int, // 2017
    val userId: Int, // 1448
    val username: String // lengjiye
)

data class RankTable(
    val curPage: Int, // 1
    val datas: List<Rank>,
    val offset: Int, // 0
    val over: Boolean, // false
    val pageCount: Int, // 342
    val size: Int, // 30
    val total: Int // 10239
)

data class Coin(
    val coinCount: Int, // 20
    val date: Long, // 1575861793000
    val desc: String, // 2019-12-09 11:23:13 签到 , 积分：10 + 10
    val id: Int, // 110399
    val reason: String, // 签到
    val type: Int, // 1
    val userId: Int, // 1448
    val userName: String // lengjiye
)

data class CoinList(
    val curPage: Int, // 1
    val datas: List<Coin>,
    val offset: Int, // 0
    val over: Boolean, // true
    val pageCount: Int, // 1
    val size: Int, // 20
    val total: Int // 11
)

data class Website(
    val desc: String,
    val icon: String,
    val id: Int, // 3810
    val link: String, // 上海
    val name: String, // zhuo liu
    val order: Int, // 0
    val userId: Int, // 1448
    val originId: Int?, // 1448
    val visible: Int // 1
)

