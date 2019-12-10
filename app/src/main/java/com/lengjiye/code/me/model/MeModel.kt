package com.lengjiye.code.me.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.me.bean.CoinList
import com.lengjiye.code.me.bean.Rank
import com.lengjiye.code.me.bean.RankTable
import com.lengjiye.code.me.bean.UserBean
import com.lengjiye.code.me.service.MeService
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServiceHolder
import io.reactivex.Observer

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = MeModel()
    }

    private fun getService(): MeService? {
        return ServiceHolder.singleton.getService(MeService::class.java)
    }

    fun getCoinRank(lifecycleOwner: LifecycleOwner, page: Int, observer: Observer<RankTable>) {
        val observable = getService()?.getCoinRank(page)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun getCoinUserInfo(lifecycleOwner: LifecycleOwner, observer: Observer<Rank>) {
        val observable = getService()?.getCoinUserInfo()?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun getCoinUserInfoList(lifecycleOwner: LifecycleOwner, page: Int, observer: Observer<CoinList>) {
        val observable = getService()?.getCoinUserInfoList(page)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }
}
