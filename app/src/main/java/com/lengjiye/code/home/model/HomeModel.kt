package com.lengjiye.code.home.model

import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.serve.HomeServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
import com.lengjiye.room.AppDatabase
import com.lengjiye.room.entity.HomeBannerEntity
import com.lengjiye.room.entity.HomeEntity
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
    fun getHomeTopAndFirstListData(observer: Observer<Pair<Boolean, List<HomeEntity>>>) {
        val localData = RxUtil.create2(object : RxUtil.RXSimpleTask<List<HomeEntity>>() {
            override fun doSth(): List<HomeEntity>? {
                val dao = AppDatabase.getInstance(CodeApplication.instance).homeDao()
                return dao.queryAll()
            }
        })?.map {
            Pair(true, it)
        }

        // 网络数据
        val observableTop = getServe()?.getArticleTop()?.map(HttpResultFunc())
        val observableList = getServe()?.getArticle(0)?.map(HttpResultFunc())?.map { t ->
            t.datas
        }
        // 合并请求
        val networkObservable = Observable.zip(observableTop, observableList,
            BiFunction<List<HomeEntity>, List<HomeEntity>, List<HomeEntity>> { t1, t2 -> t1 + t2 })?.map {
            Pair(false, it)
        }

        val observable = Observable.concat(localData, networkObservable)
        makeSubscribe(observable, observer)
    }

    /**
     * 插入数据
     */
    fun installHome2Room(list: MutableList<HomeEntity>) {
        if (list.isEmpty()) {
            return
        }
        RxUtil.justInIO {
            val dao = AppDatabase.getInstance(CodeApplication.instance).homeDao()
            val oldData = dao.queryAll()
            if (!oldData.isNullOrEmpty()) {
                dao.deleteAll(oldData)
            }
            dao.insertAll(list)
        }
    }

    fun getBanner(observer: Observer<Pair<Boolean, List<HomeBannerEntity>>>) {
        val room = RxUtil.create2(object : RxUtil.RXSimpleTask<List<HomeBannerEntity>>() {
            override fun doSth(): List<HomeBannerEntity>? {
                val dao = AppDatabase.getInstance(CodeApplication.instance).homeBannerDao()
                return dao.queryAll()
            }
        })?.map {
            Pair(true, it)
        }
        val network = getServe()?.getBanner()?.map(HttpResultFunc())?.map {
            Pair(false, it)
        }
        val observable = Observable.concat(room, network)
        makeSubscribe(observable, observer)
    }

    /**
     * 插入数据
     */
    fun installBanner2Room(list: MutableList<HomeBannerEntity>) {
        if (list.isEmpty()) {
            return
        }
        RxUtil.justInIO {
            val dao = AppDatabase.getInstance(CodeApplication.instance).homeBannerDao()
            val oldData = dao.queryAll()
            if (!oldData.isNullOrEmpty()) {
                dao.deleteAll(oldData)
            }
            dao.insertAll(list)
        }
    }


}
