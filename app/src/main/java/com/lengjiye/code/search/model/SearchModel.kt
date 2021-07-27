package com.lengjiye.code.search.model

import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.networkscope.BaseRepository.apiCall
import com.lengjiye.code.networkscope.Result
import com.lengjiye.code.networkscope.ServeHolder
import com.lengjiye.code.search.serve.SearchServe
import com.lengjiye.network.BaseModel

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

    suspend fun search(page: Int, key: String): Result<ArticleBean> {
        return apiCall { getServe()?.search(page, key) }
    }
}
