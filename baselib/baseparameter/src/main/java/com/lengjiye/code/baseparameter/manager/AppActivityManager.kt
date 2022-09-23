package com.lengjiye.code.baseparameter.manager

import androidx.fragment.app.FragmentActivity
import java.lang.ref.SoftReference


object AppActivityManager {
    /**
     * FragmentActivity 为了保证使用LifecycleOwner
     */
    var activity: SoftReference<FragmentActivity>? = null

    fun getTopActivity(): FragmentActivity? {
        if (activity == null) {
            return null
        }
        return activity?.get()
    }

    fun finishTopActivity() {
        getTopActivity()?.finish()
    }
}