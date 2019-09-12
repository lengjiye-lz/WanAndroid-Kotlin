package com.lengjiye.network

import java.util.*

class ServiceHolder {
    companion object {
        var singleton = Instance.holder

        val retrofitHolder: Int = 1
    }

    private object Instance {
        val holder = ServiceHolder()
    }

    private var list: HashMap<String, *>? = null

    private fun <T> getService(c: Class<T>, type: Int): T? {

        if (list == null) {
            list = HashMap<String, T>()
        }

        var t: T? = null

        list?.let {
            t = it[c.simpleName] as T
        }

        if (t == null) {
            when (type) {
                retrofitHolder -> {
                    t = RetrofitHolder.singleton.getRetrofit().create(c)
                }

                else -> {
                    t = RetrofitHolder.singleton.getRetrofit().create(c)
                }
            }
            t?.let {
                (list as HashMap<String, T>).put(c.simpleName, it)
            }
        }
        return t
    }

    /**
     * 获取接口
     */
    fun <T> getService(c: Class<T>): T? {
        return getService(c, retrofitHolder)
    }
}