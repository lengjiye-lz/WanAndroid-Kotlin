package com.lengjiye.code.todo.bean

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

data class TodoData(
    val completeDate: Any, // null
    val completeDateStr: String,
    val content: String,
    val date: Long, // 1590336000000
    val dateStr: String, // 2020-05-25
    val id: Int, // 23028
    val priority: Int, // 0
    val status: Int, // 0
    val title: String, // 上档次说的出
    val type: Int, // 0
    val userId: Int // 1448
)