package com.lengjiye.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_banner")
class HomeBannerEntity {
    @PrimaryKey
    val id: Int = 0 // 23
    val desc: String = ""// Android高级进阶直播课免费学习
    val imagePath: String = "" // https://wanandroid.com/blogimgs/67c28e8c-2716-4b78-95d3-22cbde65d924.jpeg
    val isVisible: Int = 0// 1
    val order: Int = 0// 0
    val title: String = ""// Android高级进阶直播课免费学习
    val type: Int = 0// 0
    val url: String = ""// https://url.163.com/4bj
}