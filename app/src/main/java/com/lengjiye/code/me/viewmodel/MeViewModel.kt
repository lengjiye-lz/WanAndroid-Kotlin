package com.lengjiye.code.me.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.me.bean.CoinList
import com.lengjiye.code.me.bean.Rank
import com.lengjiye.code.me.bean.RankTable
import com.lengjiye.code.me.model.MeModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeViewModel(application: Application) : BaseViewModel(application) {

    var rankTable = MutableLiveData<RankTable>()
    var rank = MutableLiveData<Rank>()
    var coinList = MutableLiveData<CoinList>()

    /**
     * 积分排行榜
     */
    fun getCoinRank(page: Int) {
        MeModel.singleton.getCoinRank(page)?.onEach {
            rankTable.value = it
        }?.catch { }?.launchIn(viewModelScope)
    }

    /**
     * 个人积分信息
     */
    fun getCoinUserInfo() {
        MeModel.singleton.getCoinUserInfo()?.onEach {
            rank.value = it
        }?.catch { }?.launchIn(viewModelScope)
    }

    /**
     * 个人积分获取明细
     */
    fun getCoinUserInfoList(page: Int) {
        MeModel.singleton.getCoinUserInfoList(page)?.onEach {
            coinList.value = it
        }?.catch { }?.launchIn(viewModelScope)
    }
}