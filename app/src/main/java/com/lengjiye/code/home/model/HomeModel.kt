package com.lengjiye.code.home.model

import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.bean.BannerBean
import com.lengjiye.code.home.serve.HomeServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
import com.lengjiye.room.AppDatabase
import com.lengjiye.room.entity.HomeEntity
import com.lengjiye.tools.log.LogTool
import com.lengjiye.utils.RxUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.BiFunction

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
        makeSubscribe(observableList, observer)
    }

    /**
     * 获取首页置顶和第一页的数据
     */
    fun getHomeTopAndFirstListData(observer: Observer<List<HomeEntity>>) {
        // 网络数据
        val observableTop = getServe()?.getArticleTop()?.map(HttpResultFunc())
        val observableList = getServe()?.getArticle(0)?.map(HttpResultFunc())?.map { t ->
            t.datas
        }
        // 合并请求
        val networkObservable = Observable.zip(observableTop, observableList,
            BiFunction<List<HomeEntity>, List<HomeEntity>, List<HomeEntity>> { t1, t2 -> t1 + t2 })
        makeSubscribe(networkObservable, observer)
    }

    /**
     * 获取首页置顶和第一页的数据
     */
    fun getHomeTopAndFirstListData2Room(observer: Observer<List<HomeEntity>>) {
        // 本地缓存
        val localData = Observable.create<List<HomeEntity>> {
            val dao = AppDatabase.getInstance(CodeApplication.instance).homeDao()
            val data = dao.queryAll()
            if (data == null) {
                it.onNext(arrayListOf())
            } else {
                it.onNext(data)
            }
            it.onComplete()
        }
        makeSubscribe(localData, observer)
    }

    fun getBanner(observer: Observer<List<BannerBean>>) {
        val observable = getServe()?.getBanner()?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    /**
     * 插入数据
     */
    fun installHome2Room(list: MutableList<HomeEntity>) {
        RxUtil.justInIO {
            val dao = AppDatabase.getInstance(CodeApplication.instance).homeDao()
            val oldData = dao.queryAll()
            oldData?.let { it1 -> dao.deleteAll(it1) }
            dao.insert(list)
        }
    }
}
