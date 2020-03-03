package com.lengjiye.code.main.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.home.bean.HotKey
import com.lengjiye.code.main.model.MainModel
import com.lengjiye.network.ApiException
import com.lengjiye.network.LoadingObserver

/**
 * Bessel
 */
class MainViewModel(application: Application) : BaseViewModel(application) {

    var hotkeyList = MutableLiveData<List<HotKey>>()

    private var loadingObserverHotKey: LoadingObserver<List<HotKey>>? = null

    override fun onCreate() {
        loadingObserverHotKey = LoadingObserver(object : LoadingObserver.ObserverListener<List<HotKey>> {
            override fun observerOnNext(data: List<HotKey>?) {
                hotkeyList.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })
    }

    override fun onDestroy() {
        loadingObserverHotKey?.cancelRequest()
    }

    /**
     * 获取HotKey
     */
    fun getHotKeyList(lifecycleOwner: LifecycleOwner) {
        loadingObserverHotKey?.cancelRequest()
        loadingObserverHotKey?.let {
            MainModel.singleton.getHotKeyList(lifecycleOwner, it)
        }
    }
}