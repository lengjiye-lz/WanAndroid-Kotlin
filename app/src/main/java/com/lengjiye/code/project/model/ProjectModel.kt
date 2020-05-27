package com.lengjiye.code.project.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.project.serve.ProjectServe
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
import com.lengjiye.room.AppDatabase
import com.lengjiye.room.entity.*
import com.lengjiye.utils.RxUtil
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class ProjectModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = ProjectModel()
    }

    private fun getServe(): ProjectServe? {
        return ServeHolder.singleton.getServe(ProjectServe::class.java)
    }

    fun getProjectTree(observer: Observer<List<ProjectTreeEntity>>) {
        val roomData = RxUtil.create2(object : RxUtil.RXSimpleTask<List<ProjectTreeEntity>>() {
            override fun doSth(): List<ProjectTreeEntity>? {
                val dao = AppDatabase.getInstance(CodeApplication.instance).projectTreeDao()
                return dao.queryAll()
            }
        })

        val network = getServe()?.getProjectTree()?.map(HttpResultFunc())

        val observable = Observable.concat(roomData, network)
         makeSubscribe(observable, observer)
    }

    /**
     * 插入数据åå
     */
    fun installTree2Room(list: List<ProjectTreeEntity>) {
        if (list.isEmpty()) {
            return
        }
        RxUtil.justInIO {
            val dao = AppDatabase.getInstance(CodeApplication.instance).projectTreeDao()
            val oldData = dao.queryAll()
            if (!oldData.isNullOrEmpty()) {
                dao.deleteAll(oldData)
            }
            dao.insertAll(list)
        }
    }

    fun getProjectArticle(page: Int, cid: Int, observer: Observer<ArticleBean>) {
        val observableList = getServe()?.getProjectArticle(page, cid)?.map(HttpResultFunc())
        observableList?.let { makeSubscribe(it, observer) }
    }

    /**
     * 获取缓存数据
     */
    fun getProjectArticle2Room(observer: Observer<ArticleBean>) {
        // 本地缓存
        val roomData = RxUtil.create2(object : RxUtil.RXSimpleTask<ArticleBean>() {
            override fun doSth(): ArticleBean? {
                val dao = AppDatabase.getInstance(CodeApplication.instance).projectDao()
                val data = dao.queryAll()
                return ArticleBean(0, data as List<HomeEntity>, 0, false, 0, 20, 0, 0)
            }
        })
        makeSubscribe(roomData, observer)
    }

    /**
     * 插入数据
     */
    fun installProjectArticle2Room(list: MutableList<ProjectEntity>) {
        if (list.isEmpty()){
            return
        }
        RxUtil.justInIO {
            val dao = AppDatabase.getInstance(CodeApplication.instance).projectDao()
            val oldData = dao.queryAll()
            if (!oldData.isNullOrEmpty()){
                dao.deleteAll(oldData)
            }
            dao.insertAll(list)
        }
    }

}
