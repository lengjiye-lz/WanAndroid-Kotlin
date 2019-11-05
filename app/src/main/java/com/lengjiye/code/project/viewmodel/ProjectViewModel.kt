package com.lengjiye.code.project.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.project.model.ProjectModel
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.network.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.tools.LogTool

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class ProjectViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserver: LoadingObserver<List<TreeBean>>
    private lateinit var loadingArticleObserver: LoadingObserver<ArticleBean>

    var projectTree = MutableLiveData<List<TreeBean>>()
    var projectArticle = MutableLiveData<ArticleBean>()
    private var cid = 0

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : LoadingObserver.ObserverListener<List<TreeBean>> {
            override fun observerOnNext(data: List<TreeBean>?) {
                projectTree.value = data
            }

            override fun observerOnError(e: ApiException) {
            }

        })
        loadingArticleObserver = LoadingObserver(object : LoadingObserver.ObserverListener<ArticleBean> {
            override fun observerOnNext(data: ArticleBean?) {
                data?.cid = cid
                projectArticle.value = data
            }

            override fun observerOnError(e: ApiException) {
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

    override fun onDestroy() {
        loadingObserver.cancelRequest()
        loadingArticleObserver.cancelRequest()
    }
}