package com.lengjiye.base.viewmode

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class BaseViewMode : AndroidViewModel {

    constructor(application: Application) : super(application)

    val mApplication by lazy { getApplication<Application>() }
}


