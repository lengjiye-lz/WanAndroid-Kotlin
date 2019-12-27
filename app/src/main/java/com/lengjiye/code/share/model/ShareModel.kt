package com.lengjiye.code.share.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.bean.HomeBean
import com.lengjiye.code.share.bean.ShareBean
import com.lengjiye.code.share.service.ShareService
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServiceHolder
import io.reactivex.Observer
import retrofit2.http.Field

/**
 * @Author: lz
 * @Date: 2019-12-27
 * @Description:
 */
class ShareModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = ShareModel()
    }

    private fun getService(): ShareService? {
        return ServiceHolder.singleton.getService(ShareService::class.java)
    }

    fun getCollectArticleList(lifecycleOwner: LifecycleOwner, page: Int, observer: Observer<ArticleBean>) {
        val observable = getService()?.getUserArticleList(page)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun collectDeleteWebsite(lifecycleOwner: LifecycleOwner, userId: Int, page: Int, observer: Observer<ShareBean>) {
        val observable = getService()?.collectDeleteWebsite(userId, page)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun getUserPrivateArticles(lifecycleOwner: LifecycleOwner, page: Int, observer: Observer<ShareBean>) {
        val observable = getService()?.getUserPrivateArticles(page)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun userArticleDelete(lifecycleOwner: LifecycleOwner, articleId: Int, observer: Observer<String>) {
        val observable = getService()?.userArticleDelete(articleId)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun userArticleAdd(lifecycleOwner: LifecycleOwner, title: String, link: String, observer: Observer<String>) {
        val observable = getService()?.userArticleAdd(title, link)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }
}
