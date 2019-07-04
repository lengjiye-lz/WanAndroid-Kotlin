package com.lengjiye.codelibrarykotlin

import android.app.Application
import android.content.Context
import com.lengjiye.base.inter.IApp
import com.lengjiye.base.application.MasterApplication

class CodeApplication : Application(), IApp {

    override fun getApplication(): Application {
        return this
    }

    override fun getApplicationContext(): Context {
        return applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        MasterApplication.getInstance().setIApp(this)
    }

    companion object {
        fun getApplicationContext(): Context {
            return MasterApplication.getInstance().applicationContext
        }
    }
}