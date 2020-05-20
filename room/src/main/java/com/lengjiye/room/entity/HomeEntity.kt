package com.lengjiye.room.entity

import androidx.room.*
import com.lengjiye.room.entity.converter.TagEntityConverters

@Entity(tableName = "home")
@TypeConverters(TagEntityConverters::class)
open class HomeEntity {
    @PrimaryKey(autoGenerate = true)
    var autoId: Int = 0 // 9126
    var id: Int = 0 // 9126
    var apkLink: String = ""
    var audit: Int = 0 // 1
    var author: String = "" // 鸿洋
    var chapterId: Int = 0 // 408
    var chapterName: String = "" // 鸿洋
    var collect: Boolean = false // false
    var courseId: Int = 0 // 13
    var desc: String = ""
    var envelopePic: String = ""
    var fresh: Boolean = false // false
    var link: String = "" // https://mp.weixin.qq.com/s/Js5lB7NsL-9QRK7al3bQfg
    var niceDate: String = "" // 2019-09-11
    var niceShareDate: String = "" // 未知时间
    var origin: String = ""
    var prefix: String = ""
    var projectLink: String = ""
    var publishTime: Long = 0 // 1568131200000
    var shareDate: Long = 0 // null
    var shareUser: String = ""
    var superChapterId: Int = 0 // 408
    var superChapterName: String = "" // 公众号
    var tags: List<TagEntity>? = null
    var title: String = "" // catch 住 OOM，行吗？
    var type: Int = 0 // 0
    var userId: Int = 0 // -1
    var visible: Int = 0 // 1
    var originId: Int = 0 // 1
    var zan: Int = 0 // 0
}

