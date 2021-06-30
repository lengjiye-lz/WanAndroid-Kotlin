package com.lengjiye.code.system.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.code.system.model.SystemModel
import com.lengjiye.tools.toast
import com.lengjiye.room.entity.SystemEntity
import com.lengjiye.room.entity.SystemTreeEntity
import com.lengjiye.tools.ResTool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SystemViewModel(application: Application) : BaseViewModel(application) {

    var tree = MutableLiveData<List<SystemTreeEntity>>()
    var articleBean = MutableLiveData<ArticleBean>()
    private var cid = 0

    fun getTree() {
        viewModelScope.launch {
            tree.value = SystemModel.singleton.getTreeCache()
        }
        SystemModel.singleton.getTree()?.onEach {
            tree.value = it
        }?.catch {

        }?.launchIn(viewModelScope)
    }

    fun getTreeArticleList(pager: Int, cid: Int) {
        this.cid = cid
        SystemModel.singleton.getTreeArticleList(pager, cid)?.onEach {
            it?.cid = cid
            articleBean.value = it
            withContext(Dispatchers.IO) {
                SystemModel.singleton.installTreeArticle2Room(it?.datas?.map { homeEntity ->
                    SystemEntity.toSystemEntity(homeEntity)
                } as MutableList<SystemEntity>)
            }
        }?.catch {

        }?.launchIn(viewModelScope)
    }

    fun getTreeArticleList2Room() {
        viewModelScope.launch {
            articleBean.value = SystemModel.singleton.getTreeArticleList2Room()
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