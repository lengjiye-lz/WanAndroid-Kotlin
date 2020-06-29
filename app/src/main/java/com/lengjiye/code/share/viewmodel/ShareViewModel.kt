package com.lengjiye.code.share.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.code.share.model.ShareModel
import com.lengjiye.tools.toast
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.room.entity.ShareEntity
import com.lengjiye.tools.ResTool

/**
 * @Author: lz
 * @Date: 2019-12-27
 * @Description:
 */
class ShareViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserverMoreList: LoadingObserver<ArticleBean>
    private lateinit var loadingObserverRefreshList: LoadingObserver<Pair<Boolean, ArticleBean>>
    private lateinit var loadingDefault: LoadingObserver<String>

    var shareMoreList = MutableLiveData<ArticleBean>()
    var shareRefreshList = MutableLiveData<Pair<Boolean, ArticleBean>>()

    override fun onCreate() {
        loadingObserverMoreList = LoadingObserver(object : LoadingObserver.ObserverListener<ArticleBean>() {
            override fun observerOnNext(data: ArticleBean?) {
                shareMoreList.value = data
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        loadingObserverRefreshList = LoadingObserver(object : LoadingObserver.ObserverListener<Pair<Boolean, ArticleBean>>() {
            override fun observerOnNext(data: Pair<Boolean, ArticleBean>?) {
                shareRefreshList.value = data
                if (data?.first == false) { // 只缓存当前第一页数据
                    ShareModel.singleton.installBanner2Room(data.second.datas.map {
                        ShareEntity.toShareEntity(it)
                    } as MutableList<ShareEntity>)
                }
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

    /**
     * 请求更多数据
     */
    fun getShareMoreList(page: Int) {
        loadingObserverMoreList.cancelRequest()
        ShareModel.singleton.getShareMoreList(page, loadingObserverMoreList)
    }

    /**
     * 刷新数据
     */
    fun getShareRefreshList() {
        loadingObserverRefreshList.cancelRequest()
        ShareModel.singleton.getShareRefreshList(loadingObserverRefreshList)
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
        loadingObserverRefreshList.cancelRequest()
        loadingDefault.cancelRequest()
        loadingObserverMoreList.cancelRequest()
    }
}