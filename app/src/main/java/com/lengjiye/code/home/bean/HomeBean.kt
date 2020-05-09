package com.lengjiye.code.home.bean

import com.lengjiye.room.entity.HomeEntity

/**
 * 首页
 */

data class ArticleBean(
    val curPage: Int, // 1
    val datas: List<HomeEntity>,
    val offset: Int, // 0
    val over: Boolean, // false
    val pageCount: Int, // 358
    val size: Int, // 20
    val total: Int, // 7141
    var cid: Int // 7141
)

data class BannerBean(
    val desc: String, // Android高级进阶直播课免费学习
    val id: Int, // 23
    val imagePath: String, // https://wanandroid.com/blogimgs/67c28e8c-2716-4b78-95d3-22cbde65d924.jpeg
    val isVisible: Int, // 1
    val order: Int, // 0
    val title: String, // Android高级进阶直播课免费学习
    val type: Int, // 0
    val url: String // https://url.163.com/4bj
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