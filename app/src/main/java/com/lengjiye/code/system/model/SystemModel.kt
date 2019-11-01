package com.lengjiye.code.system.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.code.system.service.SystemService
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServiceHolder
import io.reactivex.Observer

class SystemModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = SystemModel()
    }

    private fun getService(): SystemService? {
        return ServiceHolder.singleton.getService(SystemService::class.java)
    }

    fun getTree(lifecycleOwner: LifecycleOwner, observer: Observer<List<TreeBean>>) {
        val observableList = getService()?.getTree()?.map(HttpResultFunc())
        observableList?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun getTreeArticleList(lifecycleOwner: LifecycleOwner, pager: Int, cid: Int, observer: Observer<ArticleBean>) {
        val observableList = getService()?.getTreeArticleList(pager, cid)?.map(HttpResultFunc())
        observableList?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }
}
