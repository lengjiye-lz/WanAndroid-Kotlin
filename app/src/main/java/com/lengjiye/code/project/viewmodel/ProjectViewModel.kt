package com.lengjiye.code.project.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.code.project.model.ProjectModel
import com.lengjiye.tools.toast
import com.lengjiye.room.entity.ProjectEntity
import com.lengjiye.room.entity.ProjectTreeEntity
import com.lengjiye.tools.ResTool
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class ProjectViewModel(application: Application) : BaseViewModel(application) {

    var projectTree = MutableLiveData<List<ProjectTreeEntity>>()
    var projectArticle = MutableLiveData<ArticleBean>()
    private var cid = 0

    fun getProjectTree() {
        ProjectModel.singleton.getProjectTree()?.onEach {
            projectTree.value = it
        }?.catch {

        }?.launchIn(viewModelScope)
    }

    fun getProjectArticle(page: Int, cid: Int) {
        this.cid = cid
        ProjectModel.singleton.getProjectArticle(page, cid)?.onEach {
            it?.cid = cid
            projectArticle.value = it
            ProjectModel.singleton.installProjectArticle2Room(it?.datas?.map { homeEntity ->
                ProjectEntity.toProjectEntity(homeEntity)
            } as MutableList<ProjectEntity>)
        }?.catch {

        }?.launchIn(viewModelScope)
    }

    fun getProjectArticle2Room() {
        viewModelScope.launch {
            projectArticle.value = ProjectModel.singleton.getProjectArticle2Room()
        }
    }

    /**
     * 添加收藏
     */
    fun collectAddArticle(id: Int) {
        MeModel.singleton.collectAddArticle(id)?.onEach {
            ResTool.getString(R.string.s_35).toast()
        }?.catch {
            ResTool.getString(R.string.s_36).toast()
        }?.launchIn(viewModelScope)
    }

    /**
     * 取消收藏
     */
    fun unCollectArticle(id: Int) {
        MeModel.singleton.unCollectArticle(id)?.onEach {
            ResTool.getString(R.string.s_35).toast()
        }?.catch {
            ResTool.getString(R.string.s_36).toast()
        }?.launchIn(viewModelScope)
    }
}