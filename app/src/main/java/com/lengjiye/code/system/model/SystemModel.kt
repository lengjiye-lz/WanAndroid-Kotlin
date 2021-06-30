package com.lengjiye.code.system.model

import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.system.serve.SystemServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.ServeHolder
import com.lengjiye.network.transform
import com.lengjiye.room.AppDatabase
import com.lengjiye.room.entity.HomeEntity
import com.lengjiye.room.entity.SystemEntity
import com.lengjiye.room.entity.SystemTreeEntity
import kotlinx.coroutines.flow.Flow

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

    fun getTreeCache(): List<SystemTreeEntity>? {
        val dao = AppDatabase.getInstance(CodeApplication.instance).systemTreeDao()
        return dao.queryAll()
    }

    fun getTree(): Flow<List<SystemTreeEntity>?>? {
        return getServe()?.getTree()?.transform()
    }

    /**
     * 插入数据
     */
    fun installTree2Room(list: List<SystemTreeEntity>) {
        if (list.isEmpty()) {
            return
        }
        val dao = AppDatabase.getInstance(CodeApplication.instance).systemTreeDao()
        val oldData = dao.queryAll()
        if (!oldData.isNullOrEmpty()) {
            dao.deleteAll(oldData)
        }
        dao.insertAll(list)
    }

    fun getTreeArticleList(pager: Int, cid: Int): Flow<ArticleBean?>? {
        return getServe()?.getTreeArticleList(pager, cid)?.transform()
    }

    /**
     * 获取缓存数据
     */
    fun getTreeArticleList2Room(): ArticleBean {
        // 本地缓存
        val dao = AppDatabase.getInstance(CodeApplication.instance).systemDao()
        val data = dao.queryAll()
        return ArticleBean(0, data as List<HomeEntity>, 0, false, 0, 20, 0, 0)
    }

    /**
     * 插入数据
     */
    fun installTreeArticle2Room(list: MutableList<SystemEntity>) {
        if (list.isEmpty()) {
            return
        }
        val dao = AppDatabase.getInstance(CodeApplication.instance).systemDao()
        val oldData = dao.queryAll()
        if (!oldData.isNullOrEmpty()) {
            dao.deleteAll(oldData)
        }
        dao.insertAll(list)
    }
}
