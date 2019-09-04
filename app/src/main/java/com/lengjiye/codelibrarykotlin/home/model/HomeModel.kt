package com.lengjiye.codelibrarykotlin.home.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.codelibrarykotlin.home.HomeBean
import com.lengjiye.codelibrarykotlin.home.service.HomeService
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServiceHolder
import io.reactivex.Observer

class HomeModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = HomeModel()
    }

    private fun getService(): HomeService? {
        return ServiceHolder.singleton.getService(HomeService::class.java)
    }

    fun getHomeData(lifecycleOwner: LifecycleOwner, observer: Observer<HomeBean>) {
        val observable = getService()?.getsdc()?.map(HttpResultFunc())
        observable?.let {
            makeSubscribe(lifecycleOwner, it, observer)
        }
    }
}
