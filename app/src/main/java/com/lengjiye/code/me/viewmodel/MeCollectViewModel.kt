package com.lengjiye.code.me.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.bean.Website
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.code.utils.toast
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.network.LoadingObserver.ObserverListener
import com.lengjiye.tools.ResTool

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
        loadingObserverCollectArticleList = LoadingObserver(object : ObserverListener<ArticleBean>() {
            override fun observerOnNext(data: ArticleBean?) {
                articleList.value = data
            }

            override fun observerOnError(e: ApiException) {
            }

        })

        loadingObserverCollectWebsiteList = LoadingObserver(object : ObserverListener<List<Website>>() {
            override fun observerOnNext(data: List<Website>?) {
                websiteList.value = data
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
     * 获取收藏的文章的列表
     */
    fun getCollectArticleList(page: Int) {
        loadingObserverCollectArticleList.cancelRequest()
        MeModel.singleton.getCollectArticleList(page, loadingObserverCollectArticleList)
    }

    /**
     * 添加收藏
     */
    fun collectAddArticle(id: Int) {
        loadingDefault.cancelRequest()
        MeModel.singleton.collectAddArticle(id, loadingDefault)
    }

    /**
     * 添加收藏
     */
    fun collectAddOtherArticle(title: String, author: String, link: String) {
        loadingDefault.cancelRequest()
        MeModel.singleton.collectAddOtherArticle(title, author, link, loadingDefault)
    }

    /**
     * 取消收藏
     */
    fun unMyCollectArticle(id: Int, originId: Int) {
        loadingDefault.cancelRequest()
        MeModel.singleton.unMyCollectArticle(id, originId, loadingDefault)
    }

    /**
     * 获取收藏的网站的列表
     */
    fun getCollectWebsiteList() {
        loadingObserverCollectWebsiteList.cancelRequest()
        MeModel.singleton.getCollectWebsiteList(loadingObserverCollectWebsiteList)
    }

    /**
     * 添加收藏网站
     */
    fun collectAddWebsite(name: String, link: String) {
        loadingDefault.cancelRequest()
        MeModel.singleton.collectAddWebsite(name, link, loadingDefault)
    }

    /**
     * 更新
     */
    fun collectUpdateWebsite(id: Int, name: String, link: String) {
        loadingDefault.cancelRequest()
        MeModel.singleton.collectUpdateWebsite(id, name, link, loadingDefault)
    }

    /**
     * 删除
     */
    fun collectDeleteWebsite(id: Int) {
        loadingDefault.cancelRequest()
        MeModel.singleton.collectDeleteWebsite(id, loadingDefault)
    }

    override fun onCleared() {
        super.onCleared()
        loadingObserverCollectArticleList.cancelRequest()
        loadingObserverCollectWebsiteList.cancelRequest()
        loadingDefault.cancelRequest()
    }
}