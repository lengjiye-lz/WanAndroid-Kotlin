package com.lengjiye.code.login.model

import com.lengjiye.code.login.serve.LoginServe
import com.lengjiye.code.me.bean.UserBean
import com.lengjiye.network.BaseModel
import com.lengjiye.network.ServeHolder
import com.lengjiye.network.transform
import kotlinx.coroutines.flow.Flow

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

    private fun getServe(): LoginServe? {
        return ServeHolder.singleton.getServe(LoginServe::class.java)
    }

    fun login(username: String, password: String):Flow<UserBean?>? {
        return getServe()?.login(username, password)?.transform()
    }

    fun register(username: String, password: String, rePassword: String):Flow<UserBean?>? {
        return getServe()?.register(username, password, rePassword)?.transform()
    }

    fun logout():Flow<String?>? {
        return getServe()?.logout()?.transform()
    }
}
