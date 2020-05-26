package com.lengjiye.code.login.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.code.login.serve.LoginServe
import com.lengjiye.code.me.bean.UserBean
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
import io.reactivex.Observer

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

    fun login(username: String, password: String, observer: Observer<UserBean>) {
        val observable = getServe()?.login(username, password)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun register(username: String, password: String, rePassword: String, observer: Observer<UserBean>) {
        val observable = getServe()?.register(username, password, rePassword)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun logout(observer: Observer<String>) {
        val observable = getServe()?.logout()?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }
}
