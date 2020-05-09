package com.lengjiye.code.me.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.bean.CoinList
import com.lengjiye.code.me.bean.Rank
import com.lengjiye.code.me.bean.RankTable
import com.lengjiye.code.me.bean.Website
import com.lengjiye.code.me.serve.MeServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
import io.reactivex.Observer

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = MeModel()
    }

    private fun getServe(): MeServe? {
        return ServeHolder.singleton.getServe(MeServe::class.java)
    }

    fun getCoinRank(page: Int, observer: Observer<RankTable>) {
        val observable = getServe()?.getCoinRank(page)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun getCoinUserInfo(observer: Observer<Rank>) {
        val observable = getServe()?.getCoinUserInfo()?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun getCoinUserInfoList(page: Int, observer: Observer<CoinList>) {
        val observable = getServe()?.getCoinUserInfoList(page)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun getCollectArticleList(page: Int, observer: Observer<ArticleBean>) {
        val observable = getServe()?.getCollectArticleList(page)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun collectAddArticle(id: Int, observer: Observer<String>) {
        val observable = getServe()?.collectAddArticle(id)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun collectAddOtherArticle(title: String, author: String, link: String, observer: Observer<String>) {
        val observable = getServe()?.collectAddOtherArticle(title, author, link)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun unCollectArticle(id: Int, observer: Observer<String>) {
        val observable = getServe()?.unCollectArticle(id)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun unMyCollectArticle(id: Int, originId: Int, observer: Observer<String>) {
        val observable = getServe()?.unMyCollectArticle(id, originId)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun getCollectWebsiteList(observer: Observer<List<Website>>) {
        val observable = getServe()?.collectWebsiteLis()?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun collectAddWebsite(name: String, link: String, observer: Observer<String>) {
        val observable = getServe()?.collectAddWebsite(name, link)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun collectUpdateWebsite(id: Int, name: String, link: String, observer: Observer<String>) {
        val observable = getServe()?.collectUpdateWebsite(id, name, link)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun collectDeleteWebsite(id: Int, observer: Observer<String>) {
        val observable = getServe()?.collectDeleteWebsite(id)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }
}
