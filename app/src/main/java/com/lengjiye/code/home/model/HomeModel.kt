package com.lengjiye.code.home.model

import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.serve.HomeServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.ServeHolder
import com.lengjiye.network.transform
import com.lengjiye.room.AppDatabase
import com.lengjiye.room.entity.HomeBannerEntity
import com.lengjiye.room.entity.HomeEntity
import kotlinx.coroutines.flow.*

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

    fun getHomeListData(page: Int): Flow<ArticleBean?>? {
        return getServe()?.getArticle(page)?.transform()
    }

    fun getHomeTopAndFirstLocalListData(): List<HomeEntity>? {
        val dao = AppDatabase.getInstance(CodeApplication.instance).homeDao()
        return dao.queryAll()
    }

    /**
     * 获取首页置顶和第一页的数据
     */
    fun getHomeTopAndFirstListData(): Flow<ArrayList<HomeEntity>>? {
        val flowTop = getServe()?.getArticleTop()?.transform()
        val flowList = getServe()?.getArticle(0)?.transform()?.map { t ->
            t?.datas
        }
        return flowTop?.combineTransform(flowList!!) { flowTop, flowList ->
            val value = arrayListOf<HomeEntity>()
            value.addAll(flowTop?.asIterable()!!)
            value.addAll(flowList?.asIterable()!!)
            emit(value)
        }
    }

    /**
     * 插入数据
     */
    fun installHome2Room(list: MutableList<HomeEntity>) {
        if (list.isEmpty()) {
            return
        }
        val dao = AppDatabase.getInstance(CodeApplication.instance).homeDao()
        val oldData = dao.queryAll()
        if (!oldData.isNullOrEmpty()) {
            dao.deleteAll(oldData)
        }
        dao.insertAll(list)
    }

    fun getBannerLocal(): List<HomeBannerEntity>? {
        val dao = AppDatabase.getInstance(CodeApplication.instance).homeBannerDao()
        return dao.queryAll()
    }

    fun getBanner(): Flow<List<HomeBannerEntity>?>? {
        return getServe()?.getBanner()?.transform()
    }

    /**
     * 插入数据
     */
    fun installBanner2Room(list: MutableList<HomeBannerEntity>) {
        if (list.isEmpty()) {
            return
        }
        val dao = AppDatabase.getInstance(CodeApplication.instance).homeBannerDao()
        val oldData = dao.queryAll()
        if (!oldData.isNullOrEmpty()) {
            dao.deleteAll(oldData)
        }
        dao.insertAll(list)
    }
}
