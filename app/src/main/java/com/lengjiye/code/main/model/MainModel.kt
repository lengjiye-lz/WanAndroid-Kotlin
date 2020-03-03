package com.lengjiye.code.main.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.home.bean.HotKey
import com.lengjiye.code.main.service.MainService
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServiceHolder
import io.reactivex.Observer

class MainModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = MainModel()
    }

    private fun getService(): MainService? {
        return ServiceHolder.singleton.getService(MainService::class.java)
    }

    fun getHotKeyList(lifecycleOwner: LifecycleOwner, observer: Observer<List<HotKey>>) {
        val observable = getService()?.getHotKeyList()?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }
}
