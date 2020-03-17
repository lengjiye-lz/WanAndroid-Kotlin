package com.lengjiye.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.constant.ErrorCode

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * 错误处理
     */
    val errorCode = MutableLiveData<Any>()

    abstract fun onCreate()

    abstract fun onDestroy()
}


