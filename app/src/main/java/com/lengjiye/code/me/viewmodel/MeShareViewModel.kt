package com.lengjiye.code.me.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.share.bean.ShareBean
import com.lengjiye.code.share.model.ShareModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeShareViewModel() : BaseViewModel(CodeApplication.instance) {

    var shareArticles = MutableLiveData<ShareBean>()

    fun getUserShareArticles(userId: Int, page: Int) {
        ShareModel.singleton.getUserShareArticles(userId, page)?.onEach {
            shareArticles.value = it
        }?.catch {

        }?.launchIn(viewModelScope)
    }
}