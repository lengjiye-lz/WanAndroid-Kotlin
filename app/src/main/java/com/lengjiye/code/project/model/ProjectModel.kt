package com.lengjiye.code.project.model

import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.project.serve.ProjectServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.ServeHolder
import com.lengjiye.network.transform
import com.lengjiye.room.AppDatabase
import com.lengjiye.room.entity.*
import kotlinx.coroutines.flow.Flow

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

    fun getProjectTree(): Flow<List<ProjectTreeEntity>?>? {
//        val roomData = RxUtil.create2(object : RxUtil.RXSimpleTask<List<ProjectTreeEntity>>() {
//            override fun doSth(): List<ProjectTreeEntity>? {
//                val dao = AppDatabase.getInstance(CodeApplication.instance).projectTreeDao()
//                return dao.queryAll()
//            }
//        })
        return getServe()?.getProjectTree()?.transform()
    }

    /**
     * 插入数据åå
     */
    fun installTree2Room(list: List<ProjectTreeEntity>) {
        if (list.isEmpty()) {
            return
        }
        val dao = AppDatabase.getInstance(CodeApplication.instance).projectTreeDao()
        val oldData = dao.queryAll()
        if (!oldData.isNullOrEmpty()) {
            dao.deleteAll(oldData)
        }
        dao.insertAll(list)
    }

    fun getProjectArticle(page: Int, cid: Int): Flow<ArticleBean?>? {
        return getServe()?.getProjectArticle(page, cid)?.transform()
    }

    /**
     * 获取缓存数据
     */
    fun getProjectArticle2Room(): ArticleBean{
        val dao = AppDatabase.getInstance(CodeApplication.instance).projectDao()
        val data = dao.queryAll()
        return ArticleBean(0, data as List<HomeEntity>, 0, false, 0, 20, 0, 0)
    }

    /**
     * 插入数据
     */
    fun installProjectArticle2Room(list: MutableList<ProjectEntity>) {
        if (list.isEmpty()) {
            return
        }
        val dao = AppDatabase.getInstance(CodeApplication.instance).projectDao()
        val oldData = dao.queryAll()
        if (!oldData.isNullOrEmpty()) {
            dao.deleteAll(oldData)
        }
        dao.insertAll(list)
    }

}
