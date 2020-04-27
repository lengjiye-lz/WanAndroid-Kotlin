package com.lengjiye.code.search.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.search.model.SearchModel
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver

/**
 * @Author: lz
 * @Date: 2020-03-03
 * @Description:
 */
class SearchViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserver: LoadingObserver<ArticleBean>

    var searchBean = MutableLiveData<ArticleBean>()

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : LoadingObserver.ObserverListener<ArticleBean>() {
            override fun observerOnNext(data: ArticleBean?) {
                searchBean.value = data
            }

            override fun observerOnError(e: ApiException) {
                errorCode.value = e
            }
        })
    }

    /**
     * 登出
     */
    fun search(page: Int, key: String) {
        val newKey = key.replace(" ", ",")
        loadingObserver.cancelRequest()
        SearchModel.singleton.search(page, newKey, loadingObserver)
    }

    override fun onCleared() {
        super.onCleared()
        loadingObserver.cancelRequest()
    }

}