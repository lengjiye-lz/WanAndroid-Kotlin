package com.lengjiye.code.home.bean

/**
 * 首页
 */

data class ArticleBean(
    val curPage: Int, // 1
    val datas: List<HomeBean>,
    val offset: Int, // 0
    val over: Boolean, // false
    val pageCount: Int, // 358
    val size: Int, // 20
    val total: Int, // 7141
    var cid: Int // 7141
)

data class HomeBean(
    val apkLink: String,
    val audit: Int, // 1
    val author: String, // 鸿洋
    val chapterId: Int, // 408
    val chapterName: String, // 鸿洋
    var collect: Boolean, // false
    val courseId: Int, // 13
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean, // false
    val id: Int, // 9126
    val link: String, // https://mp.weixin.qq.com/s/Js5lB7NsL-9QRK7al3bQfg
    val niceDate: String, // 2019-09-11
    val niceShareDate: String, // 未知时间
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long, // 1568131200000
    val shareDate: Any, // null
    val shareUser: String,
    val superChapterId: Int, // 408
    val superChapterName: String, // 公众号
    val tags: List<TagBean>,
    val title: String, // catch 住 OOM，行吗？
    val type: Int, // 0
    val userId: Int, // -1
    val visible: Int, // 1
    val originId: Int?, // 1
    val zan: Int // 0
)

data class TagBean(
    val name: String, // 公众号
    val url: String // /wxarticle/list/408/1
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