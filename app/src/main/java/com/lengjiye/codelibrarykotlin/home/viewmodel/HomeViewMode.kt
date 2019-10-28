package com.lengjiye.codelibrarykotlin.home.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
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

    var article = MutableLiveData<Article>()
    var homeBeanList = MutableLiveData<List<HomeBean>>()

    private var loadingObserver: LoadingObserver<Article>? = null
    private var loadingObserverTopAndFirst: LoadingObserver<List<HomeBean>>? = null

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : ObserverListener<Article> {
            override fun observerOnNext(data: Article?) {
                LogTool.e("lz", "data:${data?.datas?.size}")
                article.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        loadingObserverTopAndFirst = LoadingObserver(object : ObserverListener<List<HomeBean>> {
            override fun observerOnNext(data: List<HomeBean>?) {
                LogTool.e("lz", "data:${data?.size}")
                homeBeanList.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })

    }

    /**
     * 获取首页列表数据
     */
    fun getHomeData(lifecycleOwner: LifecycleOwner, page: Int) {
        loadingObserver?.cancelRequest()
        loadingObserver?.let {
            HomeModel.singleton.getHomeListData(lifecycleOwner, page, it)
        }
    }

    fun getHomeTopAndFirstListData(lifecycleOwner: LifecycleOwner) {
        loadingObserverTopAndFirst?.cancelRequest()
        loadingObserverTopAndFirst?.let {
            HomeModel.singleton.getHomeTopAndFirstListData(lifecycleOwner, it)
        }
    }

    override fun onDestroy() {
        loadingObserver?.cancelRequest()
    }

}