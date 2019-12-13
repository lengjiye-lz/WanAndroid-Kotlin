package com.lengjiye.code.me.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.bean.CoinList
import com.lengjiye.code.me.bean.Rank
import com.lengjiye.code.me.bean.RankTable
import com.lengjiye.code.me.bean.Website
import com.lengjiye.code.me.service.MeService
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServiceHolder
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

    private fun getService(): MeService? {
        return ServiceHolder.singleton.getService(MeService::class.java)
    }

    fun getCoinRank(lifecycleOwner: LifecycleOwner, page: Int, observer: Observer<RankTable>) {
        val observable = getService()?.getCoinRank(page)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun getCoinUserInfo(lifecycleOwner: LifecycleOwner, observer: Observer<Rank>) {
        val observable = getService()?.getCoinUserInfo()?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun getCoinUserInfoList(lifecycleOwner: LifecycleOwner, page: Int, observer: Observer<CoinList>) {
        val observable = getService()?.getCoinUserInfoList(page)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun getCollectArticleList(lifecycleOwner: LifecycleOwner, page: Int, observer: Observer<ArticleBean>) {
        val observable = getService()?.getCollectArticleList(page)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun collectAddArticle(lifecycleOwner: LifecycleOwner, id: Int, observer: Observer<String>) {
        val observable = getService()?.collectAddArticle(id)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun collectAddOtherArticle(lifecycleOwner: LifecycleOwner, title: String, author: String, link: String, observer: Observer<String>) {
        val observable = getService()?.collectAddOtherArticle(title, author, link)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun unCollectArticle(lifecycleOwner: LifecycleOwner, id: Int, observer: Observer<String>) {
        val observable = getService()?.unCollectArticle(id)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun getCollectWebsiteList(lifecycleOwner: LifecycleOwner, observer: Observer<List<Website>>) {
        val observable = getService()?.collectWebsiteLis()?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun collectAddWebsite(lifecycleOwner: LifecycleOwner, name: String, link: String, observer: Observer<String>) {
        val observable = getService()?.collectAddWebsite(name, link)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun collectUpdateWebsite(lifecycleOwner: LifecycleOwner, id: Int, name: String, link: String, observer: Observer<String>) {
        val observable = getService()?.collectUpdateWebsite(id, name, link)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun collectDeleteWebsite(lifecycleOwner: LifecycleOwner, id: Int, observer: Observer<String>) {
        val observable = getService()?.collectDeleteWebsite(id)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }
}
