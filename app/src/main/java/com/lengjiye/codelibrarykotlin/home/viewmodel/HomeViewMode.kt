package com.lengjiye.codelibrarykotlin.home.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import com.lengjiye.base.viewmode.BaseViewMode
import com.lengjiye.codelibrarykotlin.application.CodeApplication
import com.lengjiye.codelibrarykotlin.home.HomeBean
import com.lengjiye.codelibrarykotlin.home.model.HomeModel
import io.reactivex.Observer

/**
 * Bessel
 */
class HomeViewMode(application: Application) : BaseViewMode(application) {

    fun getHomeData(lifecycleOwner: LifecycleOwner, observer: Observer<HomeBean>) {
        HomeModel.singleton.getHomeData(lifecycleOwner, observer)
    }
}