package com.lengjiye.code.share.model

import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.share.bean.ShareBean
import com.lengjiye.code.share.serve.ShareServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.ServeHolder
import com.lengjiye.network.transform
import com.lengjiye.room.AppDatabase
import com.lengjiye.room.entity.HomeEntity
import com.lengjiye.room.entity.ShareEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    fun getShareMoreList(page: Int): Flow<ArticleBean?>? {
        return getServe()?.getShareList(page)?.transform()
    }

    fun getShareRefreshListCache(): Pair<Boolean, ArticleBean?> {
        val dao = AppDatabase.getInstance(CodeApplication.instance).shareDao()
        val data = dao.queryAll()
        return Pair(true, ArticleBean(0, data as List<HomeEntity>, 0, false, 0, 20, 0, 0))
    }

    fun getShareRefreshList(): Flow<Pair<Boolean, ArticleBean?>>? {
//        val roomData = RxUtil.create2(object : RxUtil.RXSimpleTask<ArticleBean>() {
//            override fun doSth(): ArticleBean? {
//                val dao = AppDatabase.getInstance(CodeApplication.instance).shareDao()
//                val data = dao.queryAll()
//                return ArticleBean(0, data as List<HomeEntity>, 0, false, 0, 20, 0, 0)
//            }
//        })?.map {
//            Pair(true, it)
//        }
        return getServe()?.getShareList(0)?.transform()?.map {
            Pair(false, it)
        }
    }

    /**
     * 插入数据
     */
    fun installBanner2Room(list: MutableList<ShareEntity>) {
        if (list.isEmpty()) {
            return
        }
        val dao = AppDatabase.getInstance(CodeApplication.instance).shareDao()
        val oldData = dao.queryAll()
        if (!oldData.isNullOrEmpty()) {
            dao.deleteAll(oldData)
        }
        dao.insertAll(list)
    }

    fun getUserShareArticles(userId: Int, page: Int):Flow<ShareBean?>? {
        return getServe()?.getUserShareArticles(userId, page)?.transform()
        
    }

    fun getUserPrivateArticles(page: Int):Flow<ShareBean?>? {
        return getServe()?.getUserPrivateArticles(page)?.transform()
        
    }

    fun userArticleDelete(articleId: Int):Flow<String?>? {
        return getServe()?.userArticleDelete(articleId)?.transform()
        
    }

    fun userArticleAdd(title: String, link: String):Flow<String?>? {
        return getServe()?.userArticleAdd(title, link)?.transform()
        
    }
}
