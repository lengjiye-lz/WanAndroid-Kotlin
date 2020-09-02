package com.lengjiye.code.utils

import android.content.Context

/**
 * demo
 */
class WorkSingleton private constructor(context: Context) {
    companion object : SingletonHolder<WorkSingleton, Context>(::WorkSingleton)
}

/**
 * 单利模式
 *
 * 可传入参数
 */
open class SingletonHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator

    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}