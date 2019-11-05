package com.lengjiye.code.project.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.project.service.ProjectService
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServiceHolder
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

    private fun getService(): ProjectService? {
        return ServiceHolder.singleton.getService(ProjectService::class.java)
    }

    fun getProjectTree(lifecycleOwner: LifecycleOwner, observer: Observer<List<TreeBean>>) {
        val observableList = getService()?.getProjectTree()?.map(HttpResultFunc())
        observableList?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    fun getProjectArticle(lifecycleOwner: LifecycleOwner, page: Int, cid: Int, observer: Observer<ArticleBean>) {
        val observableList = getService()?.getProjectArticle(page, cid)?.map(HttpResultFunc())
        observableList?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

}
