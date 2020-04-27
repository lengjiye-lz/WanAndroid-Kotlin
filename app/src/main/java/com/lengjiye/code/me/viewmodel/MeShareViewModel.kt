package com.lengjiye.code.me.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.share.bean.ShareBean
import com.lengjiye.code.share.model.ShareModel
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.network.LoadingObserver.ObserverListener

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeShareViewModel() : BaseViewModel(CodeApplication.instance) {

    private lateinit var loadingObserverUserShareArticles: LoadingObserver<ShareBean>

    var shareArticles = MutableLiveData<ShareBean>()

    override fun onCreate() {
        loadingObserverUserShareArticles = LoadingObserver(object : ObserverListener<ShareBean>() {
            override fun observerOnNext(data: ShareBean?) {
                shareArticles.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })
    }

    fun getUserShareArticles(userId: Int, page: Int) {
        loadingObserverUserShareArticles.cancelRequest()
        ShareModel.singleton.getUserShareArticles(userId, page, loadingObserverUserShareArticles)
    }


    override fun onCleared() {
        super.onCleared()
        loadingObserverUserShareArticles.cancelRequest()
    }
}