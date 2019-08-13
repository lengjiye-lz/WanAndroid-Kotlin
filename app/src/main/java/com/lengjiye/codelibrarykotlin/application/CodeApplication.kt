package com.lengjiye.codelibrarykotlin.application

import android.app.Application
import android.content.Context
import com.lengjiye.base.inter.IApp
import com.lengjiye.base.application.MasterApplication
import com.lengjiye.codelibrarykotlin.BuildConfig

/**
 * applicationContext
 * 也可以通过 MasterApplication.instance 获取
 */
class CodeApplication : Application(), IApp {

    override val applicationContext = this
    override val applicationId = BuildConfig.APPLICATION_ID
    override val versionCode = BuildConfig.VERSION_CODE
    override val versionName = BuildConfig.VERSION_NAME
    override val isDebug = BuildConfig.DEBUG
    override val buildType = BuildConfig.BUILD_TYPE

    override fun onCreate() {
        super.onCreate()
        MasterApplication.instance.setIApp(this)
    }

    companion object {
        val application = this
    }
}

