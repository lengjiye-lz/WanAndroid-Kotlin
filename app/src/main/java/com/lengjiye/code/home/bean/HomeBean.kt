package com.lengjiye.code.home.bean

import com.lengjiye.room.entity.HomeEntity

/**
 * 首页
 */
data class ArticleBean(
    var curPage: Int, // 1
    var datas: List<HomeEntity>,
    var offset: Int, // 0
    var over: Boolean, // false
    var pageCount: Int, // 358
    var size: Int, // 20
    var total: Int, // 7141
    var cid: Int // 7141
)

/**
 * 关键字
 */
data class HotKey(
    val id: Int, // 6
    val link: String,
    val name: String, // 面试
    val order: Int, // 1
    val visible: Int // 1
)