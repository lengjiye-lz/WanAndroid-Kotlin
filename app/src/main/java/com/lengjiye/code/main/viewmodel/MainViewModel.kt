package com.lengjiye.code.main.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.home.bean.Hotkey
import com.lengjiye.code.home.model.HomeModel
import com.lengjiye.code.main.model.MainModel
import com.lengjiye.network.ApiException
import com.lengjiye.network.LoadingObserver

/**
 * Bessel
 */
class MainViewModel(application: Application) : BaseViewModel(application) {

    var hotkeyList = MutableLiveData<List<Hotkey>>()

    private var loadingObserverHotkey: LoadingObserver<List<Hotkey>>? = null

    override fun onCreate() {
        loadingObserverHotkey = LoadingObserver(object : LoadingObserver.ObserverListener<List<Hotkey>> {
            override fun observerOnNext(data: List<Hotkey>?) {
                hotkeyList.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })
    }

    override fun onDestroy() {
        loadingObserverHotkey?.cancelRequest()
    }

    /**
     * 获取Hotkey
     */
    fun getHotkeyList(lifecycleOwner: LifecycleOwner) {
        loadingObserverHotkey?.cancelRequest()
        loadingObserverHotkey?.let {
            MainModel.singleton.getHotkeyList(lifecycleOwner, it)
        }
    }
}