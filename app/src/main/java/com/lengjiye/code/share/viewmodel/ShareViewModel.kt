package com.lengjiye.code.share.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.bean.HomeBean
import com.lengjiye.code.share.model.ShareModel
import com.lengjiye.network.ApiException
import com.lengjiye.network.LoadingObserver

/**
 * @Author: lz
 * @Date: 2019-12-27
 * @Description:
 */
class ShareViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserverUserArticle: LoadingObserver<ArticleBean>

    var userArticleList = MutableLiveData<ArticleBean>()

    override fun onCreate() {
        loadingObserverUserArticle = LoadingObserver(object : LoadingObserver.ObserverListener<ArticleBean> {
            override fun observerOnNext(data: ArticleBean?) {
                userArticleList.value = data
            }

            override fun observerOnError(e: ApiException) {

            }

        })
    }

    fun getUserArticleList(lifecycleOwner: LifecycleOwner, page: Int) {
        loadingObserverUserArticle.cancelRequest()
        ShareModel.singleton.getCollectArticleList(lifecycleOwner, page, loadingObserverUserArticle)
    }

    override fun onDestroy() {
        loadingObserverUserArticle.cancelRequest()
    }
}