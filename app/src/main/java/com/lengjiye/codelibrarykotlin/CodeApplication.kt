package com.lengjiye.codelibrarykotlin

import android.app.Application
import android.content.Context
import com.lengjiye.base.inter.IApp
import com.lengjiye.base.application.MasterApplication

/**
 * application
 * 也可以通过 MasterApplication.instance 获取
 */
class CodeApplication : Application(), IApp {

    override val application = this
    override var applicationId = BuildConfig.APPLICATION_ID
    override val versionCode = BuildConfig.VERSION_CODE
    override val versionName = BuildConfig.VERSION_NAME
    override val isDebug = BuildConfig.DEBUG
    override val buildType = BuildConfig.BUILD_TYPE

    override fun onCreate() {
        super.onCreate()
        MasterApplication.instance.setIApp(this)
    }

    companion object {
        fun getApplicationContext(): Context? {
            return null
        }
    }

}