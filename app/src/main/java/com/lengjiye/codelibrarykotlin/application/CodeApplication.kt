package com.lengjiye.codelibrarykotlin.application

import android.app.Application
import android.content.Context
import com.lengjiye.base.application.MasterApplication
import com.lengjiye.base.inter.IApp
import com.lengjiye.codelibrarykotlin.BuildConfig

/**
 * applicationContext
 * 也可以通过 MasterApplication.instance 获取
 */
class CodeApplication : Application(), IApp {

    override fun applicationContext(): Context {
        return this
    }

    override fun applicationId(): String {
        return BuildConfig.APPLICATION_ID
    }

    override fun versionCode(): Int {
        return BuildConfig.VERSION_CODE
    }

    override fun versionName(): String {
        return BuildConfig.VERSION_NAME
    }

    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun buildType(): String {
        return BuildConfig.BUILD_TYPE
    }

    override fun baseUrl(): String {
        return BuildConfig.BASE_URL
    }

    override fun onCreate() {
        super.onCreate()
        MasterApplication.getInstance().setIApp(this)
    }

    companion object {
        val application = this
    }
}

