package com.lengjiye.codelibrarykotlin

import android.app.Application
import android.content.Context
import android.os.Bundle
import com.lengjiye.base.inter.IApp
import com.lengjiye.base.application.MasterApplication

class CodeApplication : Application(), IApp {

    override fun getVersionCode(): Int {
        return BuildConfig.VERSION_CODE
    }

    override fun getVersionName(): String {
        return BuildConfig.VERSION_NAME
    }

    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun getBuildType(): String {
        return BuildConfig.BUILD_TYPE
    }

    override fun getApplicationId(): String {
        return BuildConfig.APPLICATION_ID
    }

    override fun getApplication(): Context {
        return this
    }


    override fun onCreate() {
        super.onCreate()
        MasterApplication.getInstance().setIApp(this)
    }

    companion object {
        fun getApplicationContext(): Context {
            return MasterApplication.getInstance().application
        }
    }

}