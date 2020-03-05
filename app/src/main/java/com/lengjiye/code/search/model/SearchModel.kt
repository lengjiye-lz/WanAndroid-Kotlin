package com.lengjiye.code.search.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.bean.HomeBean
import com.lengjiye.code.search.service.SearchService
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServiceHolder
import io.reactivex.Observer

/**
 * @Author: lz
 * @Date: 2020-03-03
 * @Description:
 */
class SearchModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = SearchModel()
    }

    private fun getService(): SearchService? {
        return ServiceHolder.singleton.getService(SearchService::class.java)
    }

    fun search(lifecycleOwner: LifecycleOwner, page: Int, key: String, observer: Observer<ArticleBean>) {
        val observable = getService()?.search(page, key)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }
}
