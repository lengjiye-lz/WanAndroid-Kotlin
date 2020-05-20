package com.lengjiye.code.share.model

import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.share.bean.ShareBean
import com.lengjiye.code.share.serve.ShareServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
import com.lengjiye.room.AppDatabase
import com.lengjiye.room.entity.HomeEntity
import com.lengjiye.room.entity.ShareEntity
import com.lengjiye.utils.RxUtil
import io.reactivex.Observable
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

    fun getShareMoreList(page: Int, observer: Observer<ArticleBean>) {
        val observable = getServe()?.getShareList(page)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun getShareRefreshList(observer: Observer<Pair<Boolean, ArticleBean>>) {
        val roomData = RxUtil.create2(object : RxUtil.RXSimpleTask<ArticleBean>() {
            override fun doSth(): ArticleBean? {
                val dao = AppDatabase.getInstance(CodeApplication.instance).shareDao()
                val data = dao.queryAll()
                return ArticleBean(0, data as List<HomeEntity>, 0, false, 0, 20, 0, 0)
            }
        })?.map {
            Pair(true, it)
        }
        val observableNetWork = getServe()?.getShareList(0)?.map(HttpResultFunc())?.map {
            Pair(false, it)
        }
        val observable = Observable.concat(roomData, observableNetWork)
        makeSubscribe(observable, observer)
    }

    /**
     * 插入数据
     */
    fun installBanner2Room(list: MutableList<ShareEntity>) {
        if (list.isEmpty()) {
            return
        }
        RxUtil.justInIO {
            val dao = AppDatabase.getInstance(CodeApplication.instance).shareDao()
            val oldData = dao.queryAll()
            if (!oldData.isNullOrEmpty()) {
                dao.deleteAll(oldData)
            }
            dao.insertAll(list)
        }
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
