package com.lengjiye.code.main.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.home.bean.HotKey
import com.lengjiye.code.main.model.MainModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Bessel
 */
class MainViewModel(application: Application) : BaseViewModel(application) {

    var hotKeyList = MutableLiveData<List<HotKey>>()

    /**
     * 获取HotKey
     */
    fun getHotKeyList() {
        MainModel.singleton.getHotKeyList()?.onEach {
            hotKeyList.value = it
        }?.catch {

        }?.launchIn(viewModelScope)
    }
}