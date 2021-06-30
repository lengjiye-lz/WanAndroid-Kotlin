package com.lengjiye.code.share.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.me.model.MeModel
import com.lengjiye.code.share.model.ShareModel
import com.lengjiye.tools.toast
import com.lengjiye.room.entity.ShareEntity
import com.lengjiye.tools.ResTool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author: lz
 * @Date: 2019-12-27
 * @Description:
 */
class ShareViewModel(application: Application) : BaseViewModel(application) {

    var shareMoreList = MutableLiveData<ArticleBean>()
    var shareRefreshList = MutableLiveData<Pair<Boolean, ArticleBean?>>()

    /**
     * 请求更多数据
     */
    fun getShareMoreList(page: Int) {
        ShareModel.singleton.getShareMoreList(page)?.onEach {
            shareMoreList.value = it
        }?.catch {

        }?.launchIn(viewModelScope)
    }

    /**
     * 刷新数据
     */
    fun getShareRefreshList() {
        viewModelScope.launch {
            shareRefreshList.value = ShareModel.singleton.getShareRefreshListCache()
        }

        ShareModel.singleton.getShareRefreshList()?.onEach {
            shareRefreshList.value = it
            if (!it.first) { // 只缓存当前第一页数据
                withContext(Dispatchers.IO){
                    ShareModel.singleton.installBanner2Room(it.second?.datas?.map { homeEntity ->
                        ShareEntity.toShareEntity(homeEntity)
                    } as MutableList<ShareEntity>)
                }
            }
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