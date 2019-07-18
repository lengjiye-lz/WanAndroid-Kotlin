package com.lengjiye.base.viewmode

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lengjiye.base.model.BaseModel

open class BaseViewMode<M : BaseModel> : AndroidViewModel {
    var mModel: M? = null

    constructor(application: Application) : super(application)

    constructor(application: Application, model: M) : this(application) {
        this.mModel = model
    }

    val mApplication by lazy { getApplication<Application>() }
}


