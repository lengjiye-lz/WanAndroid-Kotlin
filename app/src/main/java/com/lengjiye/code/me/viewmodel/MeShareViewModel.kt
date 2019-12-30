package com.lengjiye.code.me.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.bean.CoinList
import com.lengjiye.code.me.bean.Rank
import com.lengjiye.code.me.bean.RankTable
import com.lengjiye.code.me.bean.Website
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.code.share.bean.ShareBean
import com.lengjiye.code.share.model.ShareModel
import com.lengjiye.network.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.network.LoadingObserver.ObserverListener

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeShareViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserverUserShareArticles: LoadingObserver<ShareBean>

    var shareArticles = MutableLiveData<ShareBean>()

    override fun onCreate() {
        loadingObserverUserShareArticles = LoadingObserver(object : ObserverListener<ShareBean> {
            override fun observerOnNext(data: ShareBean?) {
                shareArticles.value = data
            }

            override fun observerOnError(e: ApiException) {

            }

        })
    }

    fun getUserShareArticles(lifecycleOwner: LifecycleOwner, userId: Int, page: Int) {
        loadingObserverUserShareArticles.cancelRequest()
        ShareModel.singleton.getUserShareArticles(lifecycleOwner, userId, page, loadingObserverUserShareArticles)
    }


    override fun onDestroy() {

    }
}