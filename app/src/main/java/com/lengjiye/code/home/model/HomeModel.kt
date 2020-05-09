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
        val observableTop = getServe()?.getArticleTop()?.map(HttpResultFunc())
        val observableList = getServe()?.getArticle(0)?.map(HttpResultFunc())?.map { t ->
            LogTool.e("lz", "thread:${Thread.currentThread().name}")
            t.datas
        }
        LogTool.e("lz", "getHomeTopAndFirstListData:${Thread.currentThread().name}")
        // 合并请求
        val observable = Observable.concat(observableTop, observableList)
        makeSubscribe(observable, observer)
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
            oldData?.forEach {
                dao.delete(it)
            }
            dao.insert(list)
        }
    }

    fun queryHome2Room(): List<HomeEntity> {
        val list = arrayListOf<HomeEntity>()
        RxUtil.create(object : RxUtil.RXSimpleTask<List<HomeEntity>>() {
            override fun doSth(): List<HomeEntity>? {
                val dao = AppDatabase.getInstance(CodeApplication.instance).homeDao()
                val oldData = dao.queryAll()
                return oldData ?: arrayListOf()
            }

            override fun onNext(data: List<HomeEntity>) {
                super.onNext(data)
                if (data.isEmpty()) {
                    return
                }
                list.addAll(data)
            }
        })
        return list
    }
}
