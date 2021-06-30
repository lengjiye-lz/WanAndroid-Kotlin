package com.lengjiye.code.me.model

import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.bean.CoinList
import com.lengjiye.code.me.bean.Rank
import com.lengjiye.code.me.bean.RankTable
import com.lengjiye.code.me.bean.Website
import com.lengjiye.code.me.serve.MeServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.ServeHolder
import com.lengjiye.network.transform
import kotlinx.coroutines.flow.Flow

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = MeModel()
    }

    private fun getServe(): MeServe? {
        return ServeHolder.singleton.getServe(MeServe::class.java)
    }

    fun getCoinRank(page: Int): Flow<RankTable?>? {
        return getServe()?.getCoinRank(page)?.transform()
    }

    fun getCoinUserInfo(): Flow<Rank?>? {
        return getServe()?.getCoinUserInfo()?.transform()
    }

    fun getCoinUserInfoList(page: Int): Flow<CoinList?>? {
        return getServe()?.getCoinUserInfoList(page)?.transform()
    }

    fun getCollectArticleList(page: Int): Flow<ArticleBean?>? {
        return getServe()?.getCollectArticleList(page)?.transform()
    }

    fun collectAddArticle(id: Int): Flow<String?>? {
        return getServe()?.collectAddArticle(id)?.transform()
    }

    fun collectAddOtherArticle(title: String, author: String, link: String): Flow<String?>? {
        return getServe()?.collectAddOtherArticle(title, author, link)?.transform()
        
    }

    fun unCollectArticle(id: Int): Flow<String?>? {
        return getServe()?.unCollectArticle(id)?.transform()
        
    }

    fun unMyCollectArticle(id: Int, originId: Int): Flow<String?>? {
        return getServe()?.unMyCollectArticle(id, originId)?.transform()
        
    }

    fun getCollectWebsiteList(): Flow<List<Website>?>? {
        return getServe()?.collectWebsiteLis()?.transform()
        
    }

    fun collectAddWebsite(name: String, link: String): Flow<String?>? {
        return getServe()?.collectAddWebsite(name, link)?.transform()
        
    }

    fun collectUpdateWebsite(id: Int, name: String, link: String): Flow<String?>? {
        return getServe()?.collectUpdateWebsite(id, name, link)?.transform()
        
    }

    fun collectDeleteWebsite(id: Int): Flow<String?>? {
        return getServe()?.collectDeleteWebsite(id)?.transform()
        
    }
}
