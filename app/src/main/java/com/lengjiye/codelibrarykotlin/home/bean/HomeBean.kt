package com.lengjiye.codelibrarykotlin.home.bean

/**
 * 首页
 */

data class Article(
    val curPage: Int, // 1
    val datas: List<HomeBean>,
    val offset: Int, // 0
    val over: Boolean, // false
    val pageCount: Int, // 358
    val size: Int, // 20
    val total: Int // 7141
)

data class HomeBean(
    val apkLink: String,
    val audit: Int, // 1
    val author: String, // 鸿洋
    val chapterId: Int, // 408
    val chapterName: String, // 鸿洋
    val collect: Boolean, // false
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
    val tags: List<Tag>,
    val title: String, // catch 住 OOM，行吗？
    val type: Int, // 0
    val userId: Int, // -1
    val visible: Int, // 1
    val zan: Int // 0
)

data class Tag(
    val name: String, // 公众号
    val url: String // /wxarticle/list/408/1
)
