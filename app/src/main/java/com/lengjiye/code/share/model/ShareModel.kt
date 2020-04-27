package com.lengjiye.code.share.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.share.bean.ShareBean
import com.lengjiye.code.share.serve.ShareServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
import io.reactivex.Observer

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

    private fun getServe(): ShareServe? {
        return ServeHolder.singleton.getServe(ShareServe::class.java)
    }

    fun getCollectArticleList(page: Int, observer: Observer<ArticleBean>) {
        val observable = getServe()?.getUserArticleList(page)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(it, observer) }
    }

    fun getUserShareArticles(userId: Int, page: Int, observer: Observer<ShareBean>) {
        val observable = getServe()?.getUserShareArticles(userId, page)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(it, observer) }
    }

    fun getUserPrivateArticles(page: Int, observer: Observer<ShareBean>) {
        val observable = getServe()?.getUserPrivateArticles(page)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(it, observer) }
    }

    fun userArticleDelete(articleId: Int, observer: Observer<String>) {
        val observable = getServe()?.userArticleDelete(articleId)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(it, observer) }
    }

    fun userArticleAdd(title: String, link: String, observer: Observer<String>) {
        val observable = getServe()?.userArticleAdd(title, link)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(it, observer) }
    }
}
