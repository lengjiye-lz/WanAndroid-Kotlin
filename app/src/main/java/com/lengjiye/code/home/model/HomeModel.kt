package com.lengjiye.code.home.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.bean.BannerBean
import com.lengjiye.code.home.bean.HomeBean
import com.lengjiye.code.home.serve.HomeServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers

class HomeModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = HomeModel()
    }

    private fun getServe(): HomeServe? {
        return ServeHolder.singleton.getServe(HomeServe::class.java)
    }

    fun getHomeListData(page: Int, observer: Observer<ArticleBean>) {
        val observableList = getServe()?.getArticle(page)?.map(HttpResultFunc())
        observableList?.let { makeSubscribe(it, observer) }
    }

    /**
     * 获取首页置顶和第一页的数据
     */
    fun getHomeTopAndFirstListData(observer: Observer<List<HomeBean>>) {
        val observableTop = getServe()?.getArticleTop()?.map(HttpResultFunc())
        val observableList = getServe()?.getArticle(0)?.map(HttpResultFunc())
        val observableData: Observable<List<HomeBean>>
        observableList?.let {
            observableData = it.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io()).map { t -> t.datas }
            // 合并请求
            val observable = Observable.concat(observableTop, observableData)
            makeSubscribe(observable, observer)
        }
    }

    fun getBanner(observer: Observer<List<BannerBean>>) {
        val observable = getServe()?.getBanner()?.map(HttpResultFunc())
        observable?.let { makeSubscribe(it, observer) }
    }
}
