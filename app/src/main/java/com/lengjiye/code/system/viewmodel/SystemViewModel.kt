package com.lengjiye.code.system.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.code.system.model.SystemModel
import com.lengjiye.code.utils.toast
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.room.entity.SystemEntity
import com.lengjiye.room.entity.SystemTreeEntity
import com.lengjiye.tools.ResTool

class SystemViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserver: LoadingObserver<List<SystemTreeEntity>>
    private lateinit var loadingObserverArticleBean: LoadingObserver<ArticleBean>
    private lateinit var loadingObserverArticleBean2Room: LoadingObserver<ArticleBean>
    private lateinit var loadingDefault: LoadingObserver<String>

    var tree = MutableLiveData<List<SystemTreeEntity>>()
    var articleBean = MutableLiveData<ArticleBean>()
    private var cid = 0

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : LoadingObserver.ObserverListener<List<SystemTreeEntity>>() {
            override fun observerOnNext(data: List<SystemTreeEntity>?) {
                tree.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        loadingObserverArticleBean = LoadingObserver(object : LoadingObserver.ObserverListener<ArticleBean>() {
            override fun observerOnNext(data: ArticleBean?) {
                data?.cid = cid
                articleBean.value = data
                SystemModel.singleton.installTreeArticle2Room(data?.datas?.map {
                    SystemEntity.toSystemEntity(it)
                } as MutableList<SystemEntity>)
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        loadingObserverArticleBean2Room = LoadingObserver(object : LoadingObserver.ObserverListener<ArticleBean>() {
            override fun observerOnNext(data: ArticleBean?) {
                articleBean.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        loadingDefault = LoadingObserver(object : LoadingObserver.ObserverListener<String>() {
            override fun observerOnNext(data: String?) {
                ResTool.getString(R.string.s_35).toast()
            }

            override fun observerOnError(e: ApiException) {
                ResTool.getString(R.string.s_36).toast()
            }

        })
    }


    fun getTree() {
        loadingObserver.cancelRequest()
        SystemModel.singleton.getTree(loadingObserver)
    }

    fun getTreeArticleList(pager: Int, cid: Int) {
        this.cid = cid
        loadingObserverArticleBean.cancelRequest()
        SystemModel.singleton.getTreeArticleList(pager, cid, loadingObserverArticleBean)
    }

    fun getTreeArticleList2Room() {
        SystemModel.singleton.getTreeArticleList2Room(loadingObserverArticleBean2Room)
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
        loadingObserver.cancelRequest()
        loadingObserverArticleBean.cancelRequest()
    }
}