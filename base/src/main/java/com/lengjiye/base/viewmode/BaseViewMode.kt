package com.lengjiye.base.viewmode

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class BaseViewMode(application: Application) : AndroidViewModel(application) {

    val mApplication by lazy { getApplication<Application>() }

}