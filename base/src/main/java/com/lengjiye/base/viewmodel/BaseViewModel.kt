package com.lengjiye.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val mApplication by lazy { getApplication<Application>() }

    abstract fun onCreate()

    abstract fun onDestroy()
}


