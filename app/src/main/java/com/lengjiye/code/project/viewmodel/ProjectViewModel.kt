package com.lengjiye.code.project.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.code.project.model.ProjectModel
import com.lengjiye.code.system.model.SystemModel
import com.lengjiye.code.utils.toast
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.room.entity.ProjectEntity
import com.lengjiye.room.entity.ProjectTreeEntity
import com.lengjiye.tools.ResTool

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class ProjectViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserver: LoadingObserver<Pair<Boolean, List<ProjectTreeEntity>>>
    private lateinit var loadingArticleObserver: LoadingObserver<ArticleBean>
    private lateinit var loadingArticleRoomObserver: LoadingObserver<ArticleBean>
    private lateinit var loadingDefault: LoadingObserver<String>

    var projectTree = MutableLiveData<List<ProjectTreeEntity>>()
    var projectArticle = MutableLiveData<ArticleBean>()
    private var cid = 0

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : LoadingObserver.ObserverListener<Pair<Boolean, List<ProjectTreeEntity>>>() {
            override fun observerOnNext(data: Pair<Boolean, List<ProjectTreeEntity>>?) {
                projectTree.value = data?.second
                if (data?.first == false) {
                    ProjectModel.singleton.installTree2Room(data.second)
                }
            }

            override fun observerOnError(e: ApiException) {
            }
        })
        loadingArticleObserver = LoadingObserver(object : LoadingObserver.ObserverListener<ArticleBean>() {
            override fun observerOnNext(data: ArticleBean?) {
                data?.cid = cid
                projectArticle.value = data
                ProjectModel.singleton.installProjectArticle2Room(data?.datas?.map {
                    ProjectEntity.toProjectEntity(it)
                } as MutableList<ProjectEntity>)
            }

            override fun observerOnError(e: ApiException) {
            }

        })

        loadingArticleRoomObserver = LoadingObserver(object : LoadingObserver.ObserverListener<ArticleBean>() {
            override fun observerOnNext(data: ArticleBean?) {
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

    fun getProjectTree() {
        loadingObserver.cancelRequest()
        ProjectModel.singleton.getProjectTree(loadingObserver)
    }

    fun getProjectArticle(page: Int, cid: Int) {
        this.cid = cid
        loadingArticleObserver.cancelRequest()
        ProjectModel.singleton.getProjectArticle(page, cid, loadingArticleObserver)
    }

    fun getProjectArticle2Room() {
        ProjectModel.singleton.getProjectArticle2Room(loadingArticleRoomObserver)
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
        loadingArticleObserver.cancelRequest()
    }
}