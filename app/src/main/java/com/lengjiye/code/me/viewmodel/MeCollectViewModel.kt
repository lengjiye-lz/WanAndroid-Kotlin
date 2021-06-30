package com.lengjiye.code.me.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.bean.Website
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.tools.toast
import com.lengjiye.tools.ResTool
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeCollectViewModel(application: Application) : BaseViewModel(application) {

    var articleList = MutableLiveData<ArticleBean>()
    var websiteList = MutableLiveData<List<Website>>()

    /**
     * 获取收藏的文章的列表
     */
    fun getCollectArticleList(page: Int) {
        MeModel.singleton.getCollectArticleList(page)?.onEach {
            articleList.value = it
        }?.catch {

        }?.launchIn(viewModelScope)
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
     * 添加收藏
     */
    fun collectAddOtherArticle(title: String, author: String, link: String) {
        MeModel.singleton.collectAddOtherArticle(title, author, link)?.onEach {
            ResTool.getString(R.string.s_35).toast()
        }?.catch {
            ResTool.getString(R.string.s_36).toast()
        }?.launchIn(viewModelScope)
    }

    /**
     * 取消收藏
     */
    fun unMyCollectArticle(id: Int, originId: Int) {
        MeModel.singleton.unMyCollectArticle(id, originId)?.onEach {
            ResTool.getString(R.string.s_35).toast()
        }?.catch {
            ResTool.getString(R.string.s_36).toast()
        }?.launchIn(viewModelScope)
    }

    /**
     * 获取收藏的网站的列表
     */
    fun getCollectWebsiteList() {
        MeModel.singleton.getCollectWebsiteList()?.onEach {
            websiteList.value = it
        }?.catch {
            ResTool.getString(R.string.s_36).toast()
        }?.launchIn(viewModelScope)
    }

    /**
     * 添加收藏网站
     */
    fun collectAddWebsite(name: String, link: String) {
        MeModel.singleton.collectAddWebsite(name, link)?.onEach {
            ResTool.getString(R.string.s_35).toast()
        }?.catch {
            ResTool.getString(R.string.s_36).toast()
        }?.launchIn(viewModelScope)
    }

    /**
     * 更新
     */
    fun collectUpdateWebsite(id: Int, name: String, link: String) {
        MeModel.singleton.collectUpdateWebsite(id, name, link)?.onEach {
            ResTool.getString(R.string.s_35).toast()
        }?.catch {
            ResTool.getString(R.string.s_36).toast()
        }?.launchIn(viewModelScope)
    }

    /**
     * 删除
     */
    fun collectDeleteWebsite(id: Int) {
        MeModel.singleton.collectDeleteWebsite(id)?.onEach {
            ResTool.getString(R.string.s_35).toast()
        }?.catch {
            ResTool.getString(R.string.s_36).toast()
        }?.launchIn(viewModelScope)
    }
}