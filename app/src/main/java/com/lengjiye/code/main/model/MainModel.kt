package com.lengjiye.code.main.model

import com.lengjiye.code.home.bean.HotKey
import com.lengjiye.code.main.serve.MainServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.ServeHolder
import com.lengjiye.network.transform
import kotlinx.coroutines.flow.Flow

class MainModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = MainModel()
    }

    private fun getServe(): MainServe? {
        return ServeHolder.singleton.getServe(MainServe::class.java)
    }

    fun getHotKeyList():Flow<List<HotKey>?>? {
       return getServe()?.getHotKeyList()?.transform()
    }
}
