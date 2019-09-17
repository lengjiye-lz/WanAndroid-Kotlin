package com.lengjiye.base.viewmode

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewMode(application: Application) : AndroidViewModel(application) {

    val mApplication by lazy { getApplication<Application>() }

    abstract fun onCreate()

    abstract fun onDestroy()
}


