package com.lengjiye.code.me.model

import com.lengjiye.code.me.service.MeService
import com.lengjiye.network.BaseModel
import com.lengjiye.network.ServiceHolder

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = MeModel()
    }

    private fun getService(): MeService? {
        return ServiceHolder.singleton.getService(MeService::class.java)
    }
}
