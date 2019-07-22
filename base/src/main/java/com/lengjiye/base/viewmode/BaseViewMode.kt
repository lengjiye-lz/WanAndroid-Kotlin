package com.lengjiye.base.viewmode

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lengjiye.base.model.BaseModel

open class BaseViewMode : AndroidViewModel {

    constructor(application: Application) : super(application)

    val mApplication by lazy { getApplication<Application>() }
}


