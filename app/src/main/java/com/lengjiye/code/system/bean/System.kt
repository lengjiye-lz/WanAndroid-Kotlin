package com.lengjiye.code.system.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TreeBean(
    val children: List<TreeBean>,
    val courseId: Int, // 13
    val id: Int, // 150
    val name: String, // 开发环境
    val order: Int, // 1
    val parentChapterId: Int, // 0
    val userControlSetTop: Boolean, // false
    val visible: Int // 1
) : Parcelable