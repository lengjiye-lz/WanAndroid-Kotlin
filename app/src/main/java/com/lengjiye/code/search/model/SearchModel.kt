package com.lengjiye.code.search.model

import com.lengjiye.code.search.service.SearchService
import com.lengjiye.network.BaseModel
import com.lengjiye.network.ServiceHolder

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
}
