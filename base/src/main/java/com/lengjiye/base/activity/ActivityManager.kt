package com.lengjiye.base.activity

import android.app.Activity
import android.content.Context
import com.lengjiye.base.application.MasterApplication
import java.util.*

class ActivityManager {

    private val stack by lazy { Stack<Activity>() }

    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = ActivityManager()
    }

    fun add(activity: Activity) {
        stack.add(activity)
    }

    fun removeCurrent() {
        if (stack.empty()) {
            return
        }
        val a = stack.lastElement()
        a.finish()
        remove(a)
    }

    fun remove(activity: Activity) {
        if (stack.empty()) {
            return
        }
        val iterators = stack.iterator()
        while (iterators.hasNext()) {
            val currentActivity = iterators.next()
            if (currentActivity::class.java == activity::class.java) {
                stack.remove(currentActivity)
                if (!currentActivity.isFinishing) {
                    currentActivity.finish()
                }
                return
            }
        }
    }

    fun removeAll() {
        if (stack.empty()) {
            return
        }

        val iterators = stack.iterator()
        while (iterators.hasNext()) {
            val currentActivity = iterators.next()
            currentActivity.finish()
            stack.remove(currentActivity)
        }
    }

    fun size(): Int {
        return stack.size
    }

    fun getTopActivity(): Activity? {
        if (stack.empty()) {
            return null
        }
        return stack.lastElement()
    }

    /**
     * 判断应用是否在后台
     *
     * @return
     */
    fun isAppIsInBackground(): Boolean {
        val am = com.lengjiye.base.application.MasterApplication.getInstance().applicationContext()
            .getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        val runningProcesses = am.runningAppProcesses
        for (processInfo in runningProcesses) {
            //前台程序
            if (processInfo.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (activeProcess in processInfo.pkgList) {
                    if (activeProcess == com.lengjiye.base.application.MasterApplication.getInstance().applicationContext().packageName) {
                        return false
                    }
                }
            }
        }
        return true
    }
}