package com.lengjiye.code.search.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.home.bean.ArticleBean
import com.lengjiye.code.networkscope.Result
import com.lengjiye.code.search.model.SearchModel
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.tools.log.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author: lz
 * @Date: 2020-03-03
 * @Description:
 */
class SearchViewModel(application: Application) : BaseViewModel(application) {

    var searchBean = MutableLiveData<ArticleBean>()

    override fun onCreate() {}

    /**
     * 登出
     */
    fun search(page: Int, key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newKey = key.replace(" ", ",")
            val data = SearchModel.singleton.search(page, newKey)
            if (data is Result.Success) {
                withContext(Dispatchers.Main) {
                    searchBean.value = data.data
                }
            } else {
                log("失败")
            }
        }

    }
}