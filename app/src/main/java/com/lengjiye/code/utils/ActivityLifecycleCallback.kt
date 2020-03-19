package com.lengjiye.code.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.lengjiye.tools.log.LogServiceInstance

/**
 * Activity 生命周期监听
 */
class ActivityLifecycleCallback : Application.ActivityLifecycleCallbacks {

    private var refCount = 0


    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
        if (refCount == 0) {
            appGoForeGround()
        }
        refCount++
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
        refCount--
        if (refCount == 0) {
            appGoBackGround()
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    /**
     * app 进入后台
     */
    private fun appGoBackGround() {
        if (!LogServiceInstance.isShow) {
            return
        }
        LogServiceInstance.singleton.hideLog()
    }

    /**
     * app 进入前台
     */
    private fun appGoForeGround() {
        if (!LogServiceInstance.isShow) {
            return
        }
        LogServiceInstance.singleton.showLog()
    }
}