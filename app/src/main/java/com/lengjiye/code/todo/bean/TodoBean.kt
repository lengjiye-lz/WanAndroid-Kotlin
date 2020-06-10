package com.lengjiye.code.todo.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @Author: lz
 * @Date: 2020-05-25
 * @Description: id 是默认属性，不用可以删除，为了防止报错
 */
data class TodoBean(
    val curPage: Int, // 1
    val datas: List<TodoData>,
    val offset: Int, // 0
    val over: Boolean, // true
    val pageCount: Int, // 1
    val size: Int, // 20
    val total: Int // 4
)

@Parcelize
data class TodoData(
    val completeDate: Long?, // null
    val completeDateStr: String?,
    var content: String,
    val date: Long, // 1590336000000
    var dateStr: String, // 2020-05-25
    val id: Int, // 23028
    var priority: Int, // 0
    var status: Int, // 0
    var title: String, // 上档次说的出
    var type: Int, // 0
    val userId: Int // 1448
) : Parcelable