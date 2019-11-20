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
