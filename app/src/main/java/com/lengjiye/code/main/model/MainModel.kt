package com.lengjiye.code.main.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.bean.BannerBean
import com.lengjiye.code.home.bean.HomeBean
import com.lengjiye.code.home.bean.Hotkey
import com.lengjiye.code.home.service.HomeService
import com.lengjiye.code.main.service.MainService
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServiceHolder
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers

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

    fun getHotkeyList(lifecycleOwner: LifecycleOwner, observer: Observer<List<Hotkey>>) {
        val observable = getService()?.getHotkeyList()?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }
}
