package com.lengjiye.base.inter

import android.content.Context

interface IApp {

    val application: Context?

    val applicationId: String?

    val versionCode: Int?

    val versionName: String?

    val isDebug: Boolean?

    val buildType: String?
}
