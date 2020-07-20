package com.lengjiye.code.main.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.home.bean.HotKey
import com.lengjiye.code.main.model.MainModel
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import kotlinx.coroutines.CoroutineScope

/**
 * Bessel
 */
class MainViewModel(application: Application) : BaseViewModel(application) {

    var hotKeyList = MutableLiveData<List<HotKey>>()

    private var loadingObserverHotKey: LoadingObserver<List<HotKey>>? = null

    override fun onCreate() {
        loadingObserverHotKey = LoadingObserver(object : LoadingObserver.ObserverListener<List<HotKey>>() {
            override fun observerOnNext(data: List<HotKey>?) {
                hotKeyList.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        loadingObserverHotKey?.cancelRequest()
    }

    /**
     * 获取HotKey
     */
    fun getHotKeyList() {
        loadingObserverHotKey?.cancelRequest()
        loadingObserverHotKey?.let {
            MainModel.singleton.getHotKeyList(it)
        }
    }

    /**
     * 获取HotKey
     */
    fun getHotKeyList1() {
        MainModel.singleton.getHotKeyList1(viewModelScope) {

            onSuccess {
                hotKeyList.value = it
            }

            onFail { msg, errorCode ->

            }
        }
    }
}