package com.lengjiye.code.baseparameter.manager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lengjiye.code.baseparameter.constant.BaseEventConstant
import java.lang.ref.SoftReference

/**
 * Activity 生命周期监听
 */
open class ActivityLifecycleCallback : Application.ActivityLifecycleCallbacks {

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
        if (activity !is FragmentActivity) {
            return
        }
        AppActivityManager.activity = SoftReference(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        if (activity !is FragmentActivity) {
            return
        }
        val oldActivity = AppActivityManager.getTopActivity()
        if (oldActivity != activity) {
            AppActivityManager.activity = SoftReference(activity)
        }
    }

    /**
     * app 进入后台
     */
    private fun appGoBackGround() {
        LiveEventBus.get(BaseEventConstant.IS_BACK_GROUND, Boolean::class.java).post(true)
    }

    /**
     * app 进入前台
     */
    private fun appGoForeGround() {
        LiveEventBus.get(BaseEventConstant.IS_BACK_GROUND, Boolean::class.java).post(false)
    }
}