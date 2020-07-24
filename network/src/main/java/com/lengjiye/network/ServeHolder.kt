package com.lengjiye.network

import com.lengjiye.code.baseparameter.application.MasterApplication
import java.util.*

class ServeHolder {
    companion object {
        var singleton = Instance.holder
    }

    private object Instance {
        val holder = ServeHolder()
    }

    private var list: HashMap<String, *>? = null

    fun <T> getServe(c: Class<T>, url: String = MasterApplication.getInstance().baseUrl()): T? {
        if (list == null) {
            list = HashMap<String, T>()
        }

        var t: T? = null

        list?.let {
            t = it[c.simpleName] as T
        }

        if (t == null) {
            t = RetrofitHolder.createRetrofit(url).create(c)
            (list as HashMap<String, T>).put(c.simpleName, t as T)
        }
        return t
    }
}