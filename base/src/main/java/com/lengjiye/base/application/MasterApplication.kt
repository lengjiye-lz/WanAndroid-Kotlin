package com.lengjiye.base.application

import android.content.Context
import com.lengjiye.base.inter.IApp

class MasterApplication : IApp {

    // get() 不能去掉 暂时不知道为什么
    override val applicationId: String?
        get() = iApp.applicationId

    private lateinit var iApp: IApp

    override val application: Context?
        get() = iApp.application

    override val versionCode: Int?
        get() = iApp.versionCode

    override val versionName: String?
        get() = iApp.versionName

    override val isDebug: Boolean?
        get() = iApp.isDebug

    override val buildType: String?
        get() = iApp.buildType


    fun setIApp(iApp: IApp) {
        this.iApp = iApp
    }

    /**
     * 静态内部单例模式
     */
    private object Instance {
        val master = MasterApplication()
    }

    companion object {
        val instance = Instance.master
    }
}