package com.lengjiye.code.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.home.model.HomeModel
import com.lengjiye.room.entity.HomeBannerEntity
import com.lengjiye.room.entity.HomeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 数据请求接口
 */
class HomeViewModel(application: Application) : BaseViewModel(application) {

    var articleMoreList = MutableLiveData<ArticleBean>()
    var homeRefreshList = MutableLiveData<Pair<Boolean, List<HomeEntity>>>()
    var bannerList = MutableLiveData<Pair<Boolean, List<HomeBannerEntity>>>()

    /**
     * 获取首页列表数据
     */
    fun getHomeData(page: Int) {
        HomeModel.singleton.getHomeListData(page)?.onEach {
            articleMoreList.value = it
        }?.catch {

        }?.launchIn(viewModelScope)
    }

    /**
     * 获取置顶和首页数据
     */
    fun getHomeTopAndFirstListData() {
        viewModelScope.launch {
            val data = HomeModel.singleton.getHomeTopAndFirstLocalListData()
            data?.let {
                homeRefreshList.value = Pair(true, it)
            }
        }
        HomeModel.singleton.getHomeTopAndFirstListData()?.onEach {
            homeRefreshList.value = Pair(false, it)
            withContext(Dispatchers.IO){
                HomeModel.singleton.installHome2Room(it)
            }
        }?.catch {

        }?.launchIn(viewModelScope)
    }

    /**
     * 获取banner
     */
    fun getBanner() {
        viewModelScope.launch {
            val data = HomeModel.singleton.getBannerLocal()
            data?.let {
                bannerList.value = Pair(true, it)
            }
        }

        HomeModel.singleton.getBanner()?.onEach {
            bannerList.value = Pair(false, it!!)
            withContext(Dispatchers.IO){
                HomeModel.singleton.installBanner2Room(it.toMutableList())
            }
        }?.catch {

        }?.launchIn(viewModelScope)
    }

    /**
     * 添加收藏
     */
    fun collectAddArticle(id: Int) {
//        MeModel.singleton.collectAddArticle(id, loadingDefault)
    }

    /**
     * 取消收藏
     */
    fun unCollectArticle(id: Int) {
//        MeModel.singleton.unCollectArticle(id, loadingDefault)
    }
}