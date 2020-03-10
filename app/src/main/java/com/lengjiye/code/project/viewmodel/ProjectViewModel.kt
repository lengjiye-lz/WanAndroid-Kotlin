package com.lengjiye.code.project.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.code.project.model.ProjectModel
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.code.utils.toast
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.tools.ResTool

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class ProjectViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserver: LoadingObserver<List<TreeBean>>
    private lateinit var loadingArticleObserver: LoadingObserver<ArticleBean>
    private lateinit var loadingDefault: LoadingObserver<String>

    var projectTree = MutableLiveData<List<TreeBean>>()
    var projectArticle = MutableLiveData<ArticleBean>()
    private var cid = 0

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : LoadingObserver.ObserverListener<List<TreeBean>>() {
            override fun observerOnNext(data: List<TreeBean>?) {
                projectTree.value = data
            }

            override fun observerOnError(e: ApiException) {
            }

        })
        loadingArticleObserver = LoadingObserver(object : LoadingObserver.ObserverListener<ArticleBean>() {
            override fun observerOnNext(data: ArticleBean?) {
                data?.cid = cid
                projectArticle.value = data
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

    fun getProjectTree(lifecycleOwner: LifecycleOwner) {
        loadingObserver.cancelRequest()
        ProjectModel.singleton.getProjectTree(lifecycleOwner, loadingObserver)
    }

    fun getProjectArticle(lifecycleOwner: LifecycleOwner, page: Int, cid: Int) {
        this.cid = cid
        loadingArticleObserver.cancelRequest()
        ProjectModel.singleton.getProjectArticle(lifecycleOwner, page, cid, loadingArticleObserver)
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
        loadingArticleObserver.cancelRequest()
    }
}