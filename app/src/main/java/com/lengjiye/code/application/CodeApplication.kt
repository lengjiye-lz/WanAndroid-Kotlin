package com.lengjiye.code.application

import android.app.Application
import android.content.Context
import com.lengjiye.base.application.MasterApplication
import com.lengjiye.base.inter.IApp
import com.lengjiye.code.BuildConfig
import com.lengjiye.code.main.activity.MainActivity
import com.lengjiye.code.utils.CrashHandler
import com.lengjiye.tools.LogTool

/**
 * applicationContext
 * 也可以通过 MasterApplication.instance 获取
 */
class CodeApplication : Application(), IApp {

    init {
        instance = this
    }

    override fun applicationContext(): Context {
        return applicationContext
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

        // 崩溃日志捕捉
        val handler = CrashHandler()
        Thread.setDefaultUncaughtExceptionHandler(handler)

    }

    companion object {
        lateinit var instance: CodeApplication
    }
}

