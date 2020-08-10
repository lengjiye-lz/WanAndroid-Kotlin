package com.lengjiye.code.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.model.HomeModel
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.tools.toast
import com.lengjiye.network.LoadingObserver
import com.lengjiye.network.LoadingObserver.ObserverListener
import com.lengjiye.network.exception.ApiException
import com.lengjiye.room.entity.HomeBannerEntity
import com.lengjiye.room.entity.HomeEntity
import com.lengjiye.tools.ResTool

/**
 * 数据请求接口
 */
class HomeViewModel(application: Application) : BaseViewModel(application) {

    var articleMoreList = MutableLiveData<ArticleBean>()
    var homeRefreshList = MutableLiveData<Pair<Boolean, List<HomeEntity>>>()
    var bannerList = MutableLiveData<Pair<Boolean, List<HomeBannerEntity>>>()

    private lateinit var loadingMoreObserver: LoadingObserver<ArticleBean>
    private lateinit var loadingRefreshObserver: LoadingObserver<Pair<Boolean, List<HomeEntity>>>
    private lateinit var loadingBannerBeanObserver: LoadingObserver<Pair<Boolean, List<HomeBannerEntity>>>
    private lateinit var loadingDefault: LoadingObserver<String>

    override fun onCreate() {
        loadingMoreObserver = LoadingObserver(object : ObserverListener<ArticleBean>() {
            override fun observerOnNext(data: ArticleBean?) {
                articleMoreList.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        loadingRefreshObserver = LoadingObserver(object : LoadingObserver.ObserverListener<Pair<Boolean, List<HomeEntity>>>() {
            override fun observerOnNext(data: Pair<Boolean, List<HomeEntity>>?) {
                homeRefreshList.value = data
                if (data?.first == false) {
                    HomeModel.singleton.installHome2Room(data.second as MutableList<HomeEntity>)
                }
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        loadingBannerBeanObserver = LoadingObserver(object : ObserverListener<Pair<Boolean, List<HomeBannerEntity>>>() {
            override fun observerOnNext(data: Pair<Boolean, List<HomeBannerEntity>>?) {
                bannerList.value = data
                if (data?.first == false) {
                    HomeModel.singleton.installBanner2Room(data.second as MutableList<HomeBannerEntity>)
                }
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
    fun getHomeData(page: Int) {
        loadingMoreObserver.cancelRequest()
        HomeModel.singleton.getHomeListData(page, loadingMoreObserver)
    }

    /**
     * 获取置顶和首页数据
     */
    fun getHomeTopAndFirstListData() {
        loadingRefreshObserver.cancelRequest()
        HomeModel.singleton.getHomeTopAndFirstListData(loadingRefreshObserver)
    }

    /**
     * 获取banner
     */
    fun getBanner() {
        loadingBannerBeanObserver.cancelRequest()
        HomeModel.singleton.getBanner(loadingBannerBeanObserver)
    }

    /**
     * 添加收藏
     */
    fun collectAddArticle(id: Int) {
        loadingDefault.cancelRequest()
        MeModel.singleton.collectAddArticle(id, loadingDefault)
    }

    /**
     * 取消收藏
     */
    fun unCollectArticle(id: Int) {
        loadingDefault.cancelRequest()
        MeModel.singleton.unCollectArticle(id, loadingDefault)
    }

    override fun onCleared() {
        super.onCleared()
        loadingDefault.cancelRequest()
        loadingMoreObserver.cancelRequest()
        loadingRefreshObserver.cancelRequest()
        loadingBannerBeanObserver.cancelRequest()
    }
}