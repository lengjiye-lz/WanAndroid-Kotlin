package com.lengjiye.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_banner")
class HomeBannerEntity {
    @PrimaryKey
    var id: Int = 0 // 23
    var desc: String = ""// Android高级进阶直播课免费学习
    var imagePath: String = "" // https://wanandroid.com/blogimgs/67c28e8c-2716-4b78-95d3-22cbde65d924.jpeg
    var isVisible: Int = 0// 1
    var order: Int = 0// 0
    var title: String = ""// Android高级进阶直播课免费学习
    var type: Int = 0// 0
    var url: String = ""// https://url.163.com/4bj
}