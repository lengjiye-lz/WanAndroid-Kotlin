package com.lengjiye.code.project.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.project.serve.ProjectServe
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
import io.reactivex.Observer

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class ProjectModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = ProjectModel()
    }

    private fun getServe(): ProjectServe? {
        return ServeHolder.singleton.getServe(ProjectServe::class.java)
    }

    fun getProjectTree(lifecycleOwner: LifecycleOwner, observer: Observer<List<TreeBean>>) {
        val observableList = getServe()?.getProjectTree()?.map(HttpResultFunc())
        observableList?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun getProjectArticle(lifecycleOwner: LifecycleOwner, page: Int, cid: Int, observer: Observer<ArticleBean>) {
        val observableList = getServe()?.getProjectArticle(page, cid)?.map(HttpResultFunc())
        observableList?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

}
