package com.lengjiye.code.search.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.search.serve.SearchServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
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

    private fun getServe(): SearchServe? {
        return ServeHolder.singleton.getServe(SearchServe::class.java)
    }

    fun search(page: Int, key: String, observer: Observer<ArticleBean>) {
        val observable = getServe()?.search(page, key)?.map(HttpResultFunc())
        observable?.let { makeSubscribe(it, observer) }
    }
}
