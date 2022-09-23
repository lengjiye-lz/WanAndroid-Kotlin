package com.lengjiye.code.application

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.lengjiye.code.BuildConfig
import com.lengjiye.code.baseparameter.manager.AppActivityManager
import com.lengjiye.code.baseparameter.application.MasterApplication
import com.lengjiye.code.baseparameter.inter.IApp
import com.lengjiye.code.login.activity.LoginActivity
import com.lengjiye.code.utils.AccountUtils
import com.lengjiye.code.utils.CrashHandlerUtils
import com.lengjiye.code.utils.startActivity

/**
 * applicationContext
 * 也可以通过 MasterApplication.instance 获取
 */
class CodeApplication : Application(),
    IApp {

    init {
        instance = this
    }

    override fun applicationContext(): Context {
        return applicationContext
    }

    override fun application(): Application {
        return this
    }

    override fun applicationId(): String {
        return BuildConfig.APPLICATION_ID
    }

    override fun versionCode(): Int {
        return BuildConfig.VERSION_CODE
    }

    /**
     * 退出登录
     */
    override fun logout() {
        AccountUtils.logout()
        AppActivityManager.finishTopActivity()
        startActivity<LoginActivity>()
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
        val handler = CrashHandlerUtils()
        Thread.setDefaultUncaughtExceptionHandler(handler)
        debugInit()
    }

    companion object {
        lateinit var instance: CodeApplication
    }

    /**
     * debug 下才加载
     */
    private fun debugInit() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}

