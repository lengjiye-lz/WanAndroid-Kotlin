package com.lengjiye.code.me.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.me.bean.CoinList
import com.lengjiye.code.me.bean.Rank
import com.lengjiye.code.me.bean.RankTable
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.network.LoadingObserver.ObserverListener

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserver: LoadingObserver<RankTable>
    private lateinit var loadingObserverRank: LoadingObserver<Rank>
    private lateinit var loadingObserverCoinList: LoadingObserver<CoinList>

    var rankTable = MutableLiveData<RankTable>()
    var rank = MutableLiveData<Rank>()
    var coinList = MutableLiveData<CoinList>()

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : ObserverListener<RankTable> {
            override fun observerOnNext(data: RankTable?) {
                rankTable.value = data
            }

            override fun observerOnError(e: ApiException) {
            }

        })

        loadingObserverRank = LoadingObserver(object : ObserverListener<Rank> {
            override fun observerOnNext(data: Rank?) {
                rank.value = data
            }

            override fun observerOnError(e: ApiException) {
            }

        })

        loadingObserverCoinList = LoadingObserver(object : ObserverListener<CoinList> {
            override fun observerOnNext(data: CoinList?) {
                coinList.value = data
            }

            override fun observerOnError(e: ApiException) {
            }

        })
    }

    /**
     * 积分排行榜
     */
    fun getCoinRank(lifecycleOwner: LifecycleOwner, page: Int) {
        loadingObserver.cancelRequest()
        MeModel.singleton.getCoinRank(lifecycleOwner, page, loadingObserver)
    }

    /**
     * 个人积分信息
     */
    fun getCoinUserInfo(lifecycleOwner: LifecycleOwner) {
        loadingObserverRank.cancelRequest()
        MeModel.singleton.getCoinUserInfo(lifecycleOwner, loadingObserverRank)
    }

    /**
     * 个人积分获取明细
     */
    fun getCoinUserInfoList(lifecycleOwner: LifecycleOwner, page: Int) {
        loadingObserverCoinList.cancelRequest()
        MeModel.singleton.getCoinUserInfoList(lifecycleOwner, page, loadingObserverCoinList)
    }

    override fun onDestroy() {
        loadingObserver.cancelRequest()
        loadingObserverRank.cancelRequest()
        loadingObserverCoinList.cancelRequest()
    }
}