package com.lengjiye.code.system.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.code.system.model.SystemModel
import com.lengjiye.code.utils.toast
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.tools.ResTool

class SystemViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserver: LoadingObserver<List<TreeBean>>
    private lateinit var loadingObserverArticleBean: LoadingObserver<ArticleBean>
    private lateinit var loadingDefault: LoadingObserver<String>

    var tree = MutableLiveData<List<TreeBean>>()
    var articleBean = MutableLiveData<ArticleBean>()
    private var cid = 0

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : LoadingObserver.ObserverListener<List<TreeBean>>() {
            override fun observerOnNext(data: List<TreeBean>?) {
                tree.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        loadingObserverArticleBean = LoadingObserver(object : LoadingObserver.ObserverListener<ArticleBean>() {
            override fun observerOnNext(data: ArticleBean?) {
                data?.cid = cid
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


    fun getTree(lifecycleOwner: LifecycleOwner) {
        loadingObserver.cancelRequest()
        SystemModel.singleton.getTree(lifecycleOwner, loadingObserver)
    }

    fun getTreeArticleList(lifecycleOwner: LifecycleOwner, pager: Int, cid: Int) {
        this.cid = cid
        loadingObserverArticleBean.cancelRequest()
        SystemModel.singleton.getTreeArticleList(lifecycleOwner, pager, cid, loadingObserverArticleBean)
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
        loadingObserver.cancelRequest()
        loadingObserverArticleBean.cancelRequest()
    }
}