package com.lengjiye.code.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.bean.BannerBean
import com.lengjiye.code.home.model.HomeModel
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.code.utils.toast
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.network.LoadingObserver.ObserverListener
import com.lengjiye.room.entity.HomeEntity
import com.lengjiye.tools.ResTool
import com.lengjiye.tools.log.LogTool

/**
 * 数据请求接口
 */
class HomeViewModel(application: Application) : BaseViewModel(application) {

    var article = MutableLiveData<ArticleBean>()
    var homeEntityList = MutableLiveData<List<HomeEntity>>()
    var bannerList = MutableLiveData<List<BannerBean>>()


    private lateinit var loadingObserver: LoadingObserver<ArticleBean>
    private lateinit var loadingObserverTopAndFirst: LoadingObserver<List<HomeEntity>>
    private lateinit var loadingObserverBannerBean: LoadingObserver<List<BannerBean>>
    private lateinit var loadingDefault: LoadingObserver<String>

    // 数据是不是来自数据库
    private var isTopAndFirstRoom: Boolean = true

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : ObserverListener<ArticleBean>() {
            override fun observerOnNext(data: ArticleBean?) {
                article.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        loadingObserverTopAndFirst = LoadingObserver(object : LoadingObserver.ObserverListener<List<HomeEntity>>() {
            override fun observerOnNext(data: List<HomeEntity>?) {
                homeEntityList.value = data
                if (!isTopAndFirstRoom) {
                    HomeModel.singleton.installHome2Room(data as MutableList<HomeEntity>)
                }
                isTopAndFirstRoom = false
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
    fun getHomeData(page: Int) {
        loadingObserver.cancelRequest()
        loadingObserver.let {
            HomeModel.singleton.getHomeListData(page, it)
        }
    }

    /**
     * 获取置顶和首页数据
     */
    fun getHomeTopAndFirstListData() {
        loadingObserverTopAndFirst.cancelRequest()
        loadingObserverTopAndFirst.let {
            isTopAndFirstRoom = true
            HomeModel.singleton.getHomeTopAndFirstListData2Room(it)
            HomeModel.singleton.getHomeTopAndFirstListData(it)
        }
    }

    /**
     * 获取banner
     */
    fun getBanner() {
        loadingObserverBannerBean.cancelRequest()
        loadingObserverBannerBean.let {
            HomeModel.singleton.getBanner(it)
        }
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
        loadingObserver.cancelRequest()
        loadingObserverTopAndFirst.cancelRequest()
        loadingObserverBannerBean.cancelRequest()
    }
}