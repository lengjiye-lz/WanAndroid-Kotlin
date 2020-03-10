package com.lengjiye.code.home.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.bean.BannerBean
import com.lengjiye.code.home.bean.HomeBean
import com.lengjiye.code.home.model.HomeModel
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.code.utils.toast
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.network.LoadingObserver.ObserverListener
import com.lengjiye.tools.ResTool

/**
 * 数据请求接口
 */
class HomeViewModel(application: Application) : BaseViewModel(application) {

    var article = MutableLiveData<ArticleBean>()
    var homeBeanList = MutableLiveData<List<HomeBean>>()
    var bannerList = MutableLiveData<List<BannerBean>>()

    var homeBeanTopAndFirstList: MutableList<HomeBean>? = null

    private lateinit var loadingObserver: LoadingObserver<ArticleBean>
    private lateinit var loadingObserverTopAndFirst: LoadingObserver<List<HomeBean>>
    private lateinit var loadingObserverBannerBean: LoadingObserver<List<BannerBean>>
    private lateinit var loadingDefault: LoadingObserver<String>

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : ObserverListener<ArticleBean>() {
            override fun observerOnNext(data: ArticleBean?) {
                article.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        homeBeanTopAndFirstList = arrayListOf()

        loadingObserverTopAndFirst = LoadingObserver(object : LoadingObserver.ObserverListener<List<HomeBean>>() {

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

        loadingObserverBannerBean = LoadingObserver(object : ObserverListener<List<BannerBean>>() {
            override fun observerOnNext(data: List<BannerBean>?) {
                bannerList.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        loadingDefault = LoadingObserver(object : ObserverListener<String>() {
            override fun observerOnNext(data: String?) {
                ResTool.getString(R.string.s_35).toast()
            }

            override fun observerOnError(e: ApiException) {
                ResTool.getString(R.string.s_36).toast()
            }

        })
    }

    /**
     * 获取首页列表数据
     */
    fun getHomeData(lifecycleOwner: LifecycleOwner, page: Int) {
        loadingObserver.cancelRequest()
        loadingObserver.let {
            HomeModel.singleton.getHomeListData(lifecycleOwner, page, it)
        }
    }

    /**
     * 获取置顶和首页数据
     */
    fun getHomeTopAndFirstListData(lifecycleOwner: LifecycleOwner) {
        loadingObserverTopAndFirst.cancelRequest()
        loadingObserverTopAndFirst.let {
            HomeModel.singleton.getHomeTopAndFirstListData(lifecycleOwner, it)
        }
    }

    /**
     * 获取banner
     */
    fun getBanner(lifecycleOwner: LifecycleOwner) {
        loadingObserverBannerBean.cancelRequest()
        loadingObserverBannerBean.let {
            HomeModel.singleton.getBanner(lifecycleOwner, it)
        }
    }

    /**
     * 添加收藏
     */
    fun collectAddArticle(lifecycleOwner: LifecycleOwner, id: Int) {
        loadingDefault.cancelRequest()
        MeModel.singleton.collectAddArticle(lifecycleOwner, id, loadingDefault)
    }

    /**
     * 取消收藏
     */
    fun unCollectArticle(lifecycleOwner: LifecycleOwner, id: Int) {
        loadingDefault.cancelRequest()
        MeModel.singleton.unCollectArticle(lifecycleOwner, id, loadingDefault)
    }


    override fun onDestroy() {
        loadingDefault.cancelRequest()
        loadingObserver.cancelRequest()
        loadingObserverTopAndFirst.cancelRequest()
        loadingObserverBannerBean.cancelRequest()
    }

}