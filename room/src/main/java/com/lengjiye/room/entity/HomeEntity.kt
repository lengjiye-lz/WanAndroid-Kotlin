package com.lengjiye.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home")
class HomeEntity {
    @PrimaryKey
    val id: Int = 0 // 9126
    val apkLink: String = ""
    val audit: Int = 0 // 1
    val author: String = "" // 鸿洋
    val chapterId: Int = 0 // 408
    val chapterName: String = "" // 鸿洋
    var collect: Boolean = false // false
    val courseId: Int = 0 // 13
    val desc: String = ""
    val envelopePic: String = ""
    val fresh: Boolean = false // false
    val link: String = "" // https://mp.weixin.qq.com/s/Js5lB7NsL-9QRK7al3bQfg
    val niceDate: String = "" // 2019-09-11
    val niceShareDate: String = "" // 未知时间
    val origin: String = ""
    val prefix: String = ""
    val projectLink: String = ""
    val publishTime: Long = 0 // 1568131200000
    val shareDate: Any? = null // null
    val shareUser: String = ""
    val superChapterId: Int = 0 // 408
    val superChapterName: String = "" // 公众号

    @Embedded
    val tags: List<TagEntity>? = null
    val title: String = "" // catch 住 OOM，行吗？
    val type: Int = 0 // 0
    val userId: Int = 0 // -1
    val visible: Int = 0 // 1
    val originId: Int = 0 // 1
    val zan: Int = 0 // 0
}

class TagEntity {
    val name: String = "" // 公众号
    val url: String = "" // /wxarticle/list/408/1
}