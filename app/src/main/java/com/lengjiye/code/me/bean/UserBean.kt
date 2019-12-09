package com.lengjiye.code.me.bean

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description: id 是默认属性，不用可以删除，为了防止报错
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