package com.lengjiye.code.login.model

import com.lengjiye.code.login.service.LoginService
import com.lengjiye.network.BaseModel
import com.lengjiye.network.ServiceHolder

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class LoginModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = LoginModel()
    }

    private fun getService(): LoginService? {
        return ServiceHolder.singleton.getService(LoginService::class.java)
    }
}
