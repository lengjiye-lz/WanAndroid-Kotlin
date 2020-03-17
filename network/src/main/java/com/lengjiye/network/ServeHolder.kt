package com.lengjiye.network

import java.util.*

class ServeHolder {
    companion object {
        var singleton = Instance.holder

        const val retrofitHolder: Int = 1
    }

    private object Instance {
        val holder = ServeHolder()
    }

    private var list: HashMap<String, *>? = null

    private fun <T> getServe(c: Class<T>, type: Int): T? {

        if (list == null) {
            list = HashMap<String, T>()
        }

        var t: T? = null

        list?.let {
            t = it[c.simpleName] as T
        }

        if (t == null) {
            t = when (type) {
                retrofitHolder -> {
                    RetrofitHolder.singleton.getRetrofit().create(c)
                }

                else -> {
                    RetrofitHolder.singleton.getRetrofit().create(c)
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
    fun <T> getServe(c: Class<T>): T? {
        return getServe(c, retrofitHolder)
    }
}