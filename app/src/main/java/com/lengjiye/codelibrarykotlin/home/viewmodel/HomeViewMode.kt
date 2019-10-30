package com.lengjiye.codelibrarykotlin.home.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmode.BaseViewMode
import com.lengjiye.codelibrarykotlin.home.bean.ArticleBean
import com.lengjiye.codelibrarykotlin.home.bean.BannerBean
import com.lengjiye.codelibrarykotlin.home.bean.HomeBean
import com.lengjiye.codelibrarykotlin.home.model.HomeModel
import com.lengjiye.network.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.network.LoadingObserver.ObserverListener

/**
 * Bessel
 */
class HomeViewMode(application: Application) : BaseViewMode(application) {

    var article = MutableLiveData<ArticleBean>()
    var homeBeanList = MutableLiveData<List<HomeBean>>()
    var bannerList = MutableLiveData<List<BannerBean>>()

    var homeBeanTopAndFirstList: MutableList<HomeBean>? = null

    private var loadingObserver: LoadingObserver<ArticleBean>? = null
    private var loadingObserverTopAndFirst: LoadingObserver<List<HomeBean>>? = null
    private var loadingObserverBannerBean: LoadingObserver<List<BannerBean>>? = null

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : ObserverListener<ArticleBean> {
            override fun observerOnNext(data: ArticleBean?) {
                article.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        homeBeanTopAndFirstList = arrayListOf()

        loadingObserverTopAndFirst = LoadingObserver(object : LoadingObserver.ObserverListener1<List<HomeBean>> {

            override fun observerOnComplete() {
                homeBeanList.value = homeBeanTopAndFirstList
            }

            override fun observerOnNext(data: List<HomeBean>?) {
                data?.let {
                    homeBeanTopAndFirstList?.addAll(it)
                }
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        loadingObserverBannerBean = LoadingObserver(object : ObserverListener<List<BannerBean>> {
            override fun observerOnNext(data: List<BannerBean>?) {
                bannerList.value = data
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

    /**
     * 获取置顶和首页数据
     */
    fun getHomeTopAndFirstListData(lifecycleOwner: LifecycleOwner) {
        loadingObserverTopAndFirst?.cancelRequest()
        loadingObserverTopAndFirst?.let {
            HomeModel.singleton.getHomeTopAndFirstListData(lifecycleOwner, it)
        }
    }

    /**
     * 获取banner
     */
    fun getBanner(lifecycleOwner: LifecycleOwner) {
        loadingObserverBannerBean?.cancelRequest()
        loadingObserverBannerBean?.let {
            HomeModel.singleton.getBanner(lifecycleOwner, it)
        }
    }


    override fun onDestroy() {
        loadingObserver?.cancelRequest()
        loadingObserverTopAndFirst?.cancelRequest()
        loadingObserverBannerBean?.cancelRequest()
    }

}