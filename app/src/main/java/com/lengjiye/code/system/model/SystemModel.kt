package com.lengjiye.code.system.model

import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.system.serve.SystemServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
import com.lengjiye.room.AppDatabase
import com.lengjiye.room.entity.HomeEntity
import com.lengjiye.room.entity.SystemEntity
import com.lengjiye.room.entity.SystemTreeEntity
import com.lengjiye.utils.RxUtil
import io.reactivex.Observable
import io.reactivex.Observer

class SystemModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = SystemModel()
    }

    private fun getServe(): SystemServe? {
        return ServeHolder.singleton.getServe(SystemServe::class.java)
    }

    fun getTree(observer: Observer<Pair<Boolean, List<SystemTreeEntity>>>) {
        val roomData = RxUtil.create2(object : RxUtil.RXSimpleTask<List<SystemTreeEntity>>() {
            override fun doSth(): List<SystemTreeEntity>? {
                val dao = AppDatabase.getInstance(CodeApplication.instance).systemTreeDao()
                return dao.queryAll()
            }
        })?.map {
            Pair(true, it)
        }

        val network = getServe()?.getTree()?.map(HttpResultFunc())?.map {
            Pair(false, it)
        }

        val observable = Observable.concat(roomData, network)
        makeSubscribe(observable, observer)
    }

    /**
     * 插入数据
     */
    fun installTree2Room(list: List<SystemTreeEntity>) {
        if (list.isEmpty()) {
            return
        }
        RxUtil.justInIO {
            val dao = AppDatabase.getInstance(CodeApplication.instance).systemTreeDao()
            val oldData = dao.queryAll()
            if (!oldData.isNullOrEmpty()) {
                dao.deleteAll(oldData)
            }
            dao.insertAll(list)
        }
    }

    fun getTreeArticleList(pager: Int, cid: Int, observer: Observer<ArticleBean>) {
        val observableList = getServe()?.getTreeArticleList(pager, cid)?.map(HttpResultFunc())
        makeSubscribe(observableList, observer)
    }

    /**
     * 获取缓存数据
     */
    fun getTreeArticleList2Room(observer: Observer<ArticleBean>) {
        // 本地缓存
        val roomData = RxUtil.create2(object : RxUtil.RXSimpleTask<ArticleBean>() {
            override fun doSth(): ArticleBean? {
                val dao = AppDatabase.getInstance(CodeApplication.instance).systemDao()
                val data = dao.queryAll()
                return ArticleBean(0, data as List<HomeEntity>, 0, false, 0, 20, 0, 0)
            }
        })
        makeSubscribe(roomData, observer)
    }

    /**
     * 插入数据
     */
    fun installTreeArticle2Room(list: MutableList<SystemEntity>) {
        if (list.isEmpty()) {
            return
        }
        RxUtil.justInIO {
            val dao = AppDatabase.getInstance(CodeApplication.instance).systemDao()
            val oldData = dao.queryAll()
            if (!oldData.isNullOrEmpty()) {
                dao.deleteAll(oldData)
            }
            dao.insertAll(list)
        }
    }
}
