package com.lengjiye.codelibrarykotlin.home.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.lengjiye.base.viewmode.BaseViewMode
import com.lengjiye.codelibrarykotlin.home.bean.Article
import com.lengjiye.codelibrarykotlin.home.bean.HomeBean
import com.lengjiye.codelibrarykotlin.home.model.HomeModel
import com.lengjiye.network.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.network.LoadingObserver.ObserverListener
import com.lengjiye.tools.LogTool

/**
 * Bessel
 */
class HomeViewMode(application: Application) : BaseViewMode(application) {

    private var loadingObserver: LoadingObserver<Article>? = null

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : ObserverListener<Article> {
            override fun observerOnNext(data: Article?) {
                LogTool.e("data:${data}")
            }

            override fun observerOnError(e: ApiException) {

            }
        })
    }


    fun getHomeData(lifecycleOwner: LifecycleOwner, page: Int) {
        loadingObserver?.cancelRequest()
        loadingObserver?.let {
            HomeModel.singleton.getHomeData(lifecycleOwner, page, it)
        }
    }

    override fun onDestroy() {
        loadingObserver?.cancelRequest()
    }

}