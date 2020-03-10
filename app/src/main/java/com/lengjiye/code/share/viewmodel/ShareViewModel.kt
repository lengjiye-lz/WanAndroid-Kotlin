package com.lengjiye.code.share.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.code.share.model.ShareModel
import com.lengjiye.code.utils.toast
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.tools.ResTool

/**
 * @Author: lz
 * @Date: 2019-12-27
 * @Description:
 */
class ShareViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserverUserArticle: LoadingObserver<ArticleBean>
    private lateinit var loadingDefault: LoadingObserver<String>

    var userArticleList = MutableLiveData<ArticleBean>()

    override fun onCreate() {
        loadingObserverUserArticle = LoadingObserver(object : LoadingObserver.ObserverListener<ArticleBean>() {
            override fun observerOnNext(data: ArticleBean?) {
                userArticleList.value = data
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

    fun getUserArticleList(lifecycleOwner: LifecycleOwner, page: Int) {
        loadingObserverUserArticle.cancelRequest()
        ShareModel.singleton.getCollectArticleList(lifecycleOwner, page, loadingObserverUserArticle)
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
        loadingDefault.cancelRequest()
        loadingObserverUserArticle.cancelRequest()
    }
}