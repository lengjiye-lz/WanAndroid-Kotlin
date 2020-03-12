package com.lengjiye.code.system.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.code.system.serve.SystemServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
import io.reactivex.Observer

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

    fun getTree(lifecycleOwner: LifecycleOwner, observer: Observer<List<TreeBean>>) {
        val observableList = getServe()?.getTree()?.map(HttpResultFunc())
        observableList?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun getTreeArticleList(lifecycleOwner: LifecycleOwner, pager: Int, cid: Int, observer: Observer<ArticleBean>) {
        val observableList = getServe()?.getTreeArticleList(pager, cid)?.map(HttpResultFunc())
        observableList?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }
}
