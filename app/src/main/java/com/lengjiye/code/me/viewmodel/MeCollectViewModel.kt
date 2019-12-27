package com.lengjiye.code.me.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.bean.CoinList
import com.lengjiye.code.me.bean.Rank
import com.lengjiye.code.me.bean.RankTable
import com.lengjiye.code.me.bean.Website
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.network.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.network.LoadingObserver.ObserverListener

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeCollectViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserverCollectArticleList: LoadingObserver<ArticleBean>
    private lateinit var loadingObserverCollectWebsiteList: LoadingObserver<List<Website>>
    private lateinit var loadingDefault: LoadingObserver<String>

    var articleList = MutableLiveData<ArticleBean>()
    var websiteList = MutableLiveData<List<Website>>()

    override fun onCreate() {
        loadingObserverCollectArticleList = LoadingObserver(object : ObserverListener<ArticleBean> {
            override fun observerOnNext(data: ArticleBean?) {
                articleList.value = data
            }

            override fun observerOnError(e: ApiException) {
            }

        })

        loadingObserverCollectWebsiteList = LoadingObserver(object : ObserverListener<List<Website>> {
            override fun observerOnNext(data: List<Website>?) {
                websiteList.value = data
            }

            override fun observerOnError(e: ApiException) {
            }

        })

        loadingDefault = LoadingObserver(object : ObserverListener<String> {
            override fun observerOnNext(data: String?) {

            }

            override fun observerOnError(e: ApiException) {
            }

        })
    }

    /**
     * 获取收藏的文章的列表
     */
    fun getCollectArticleList(lifecycleOwner: LifecycleOwner, page: Int) {
        loadingObserverCollectArticleList.cancelRequest()
        MeModel.singleton.getCollectArticleList(lifecycleOwner, page, loadingObserverCollectArticleList)
    }

    /**
     * 添加收藏
     */
    fun collectAddArticle(lifecycleOwner: LifecycleOwner, id: Int) {
        loadingDefault.cancelRequest()
        MeModel.singleton.collectAddArticle(lifecycleOwner, id, loadingDefault)
    }

    /**
     * 添加收藏
     */
    fun collectAddOtherArticle(lifecycleOwner: LifecycleOwner, title: String, author: String, link: String) {
        loadingDefault.cancelRequest()
        MeModel.singleton.collectAddOtherArticle(lifecycleOwner, title, author, link, loadingDefault)
    }

    /**
     * 取消收藏
     */
    fun uncollectArticle(lifecycleOwner: LifecycleOwner, id: Int) {
        loadingDefault.cancelRequest()
        MeModel.singleton.unCollectArticle(lifecycleOwner, id, loadingDefault)
    }

    /**
     * 获取收藏的网站的列表
     */
    fun getCollectWebsiteList(lifecycleOwner: LifecycleOwner) {
        loadingObserverCollectWebsiteList.cancelRequest()
        MeModel.singleton.getCollectWebsiteList(lifecycleOwner, loadingObserverCollectWebsiteList)
    }

    /**
     * 添加收藏网站
     */
    fun collectAddWebsite(lifecycleOwner: LifecycleOwner, name: String, link: String) {
        loadingDefault.cancelRequest()
        MeModel.singleton.collectAddWebsite(lifecycleOwner, name, link, loadingDefault)
    }

    /**
     * 更新
     */
    fun collectUpdateWebsite(lifecycleOwner: LifecycleOwner, id: Int, name: String, link: String) {
        loadingDefault.cancelRequest()
        MeModel.singleton.collectUpdateWebsite(lifecycleOwner, id, name, link, loadingDefault)
    }

    /**
     * 删除
     */
    fun collectDeleteWebsite(lifecycleOwner: LifecycleOwner, id: Int) {
        loadingDefault.cancelRequest()
        MeModel.singleton.collectDeleteWebsite(lifecycleOwner, id, loadingDefault)
    }

    override fun onDestroy() {
        loadingObserverCollectArticleList.cancelRequest()
        loadingObserverCollectWebsiteList.cancelRequest()
        loadingDefault.cancelRequest()
    }
}