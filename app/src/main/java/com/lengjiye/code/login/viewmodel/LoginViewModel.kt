package com.lengjiye.code.login.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.constant.ErrorCode
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.login.model.LoginModel
import com.lengjiye.code.me.bean.UserBean
import com.lengjiye.code.utils.AccountUtil
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.LoadingObserver
import com.lengjiye.tools.ResTool

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class LoginViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserver: LoadingObserver<UserBean>
    private lateinit var loadingObserverRegister: LoadingObserver<UserBean>
    private lateinit var loadingObserverLogout: LoadingObserver<String>
    var loginSuc = MutableLiveData<Boolean>()
    var registerSuc = MutableLiveData<Boolean>()

    var logoutSuc = MutableLiveData<Boolean>()

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : LoadingObserver.ObserverListener<UserBean>() {
            override fun observerOnNext(data: UserBean?) {
                data?.let {
                    saveAccount(it)
                    loginSuc.value = true
                }
            }

            override fun observerOnError(e: ApiException) {
                errorCode.value = e
                loginSuc.value = false
            }
        })

        loadingObserverRegister = LoadingObserver(object : LoadingObserver.ObserverListener<UserBean>() {
            override fun observerOnNext(data: UserBean?) {
                data?.let {
                    saveAccount(it)
                    registerSuc.value = true
                }
            }

            override fun observerOnError(e: ApiException) {

            }
        })

        loadingObserverLogout = LoadingObserver(object : LoadingObserver.ObserverListener<String>() {
            override fun observerOnNext(data: String?) {
                AccountUtil.logout()
                logoutSuc.value = true
            }

            override fun observerOnError(e: ApiException) {
                logoutSuc.value = false
            }
        })
    }

    /**
     * 登录
     */
    fun login(username: String?, password: String?) {
        if (username.isNullOrEmpty()) {
            errorCode.value = ApiException(ErrorCode.nameError, ResTool.getString(R.string.s_9), null)
            return
        }
        AccountUtil.setUserName(username)
        if (password.isNullOrEmpty()) {
            errorCode.value = ApiException(ErrorCode.passError, ResTool.getString(R.string.s_10), null)
            return
        }
        loadingObserver.cancelRequest()
        LoginModel.singleton.login(username, password, loadingObserver)
    }

    /**
     * 注册
     */
    fun register(username: String?, password: String?, rePassword: String?) {
        if (username.isNullOrEmpty()) {
            errorCode.value = ApiException(ErrorCode.nameError, ResTool.getString(R.string.s_9), null)
            return
        }
        AccountUtil.setUserName(username)
        if (password.isNullOrEmpty()) {
            errorCode.value = ApiException(ErrorCode.passError, ResTool.getString(R.string.s_10), null)
            return
        }

        if (rePassword.isNullOrEmpty()) {
            errorCode.value = ApiException(ErrorCode.rePassError, ResTool.getString(R.string.s_11), null)
            return
        }
        loadingObserverRegister.cancelRequest()
        LoginModel.singleton.register(username, password, rePassword, loadingObserverRegister)
    }

    /**
     * 登出
     */
    fun logout() {
        loadingObserverLogout.cancelRequest()
        LoginModel.singleton.logout(loadingObserverLogout)
    }

    /**
     * 保存账号
     */
    private fun saveAccount(user: UserBean) {
        // TODO  保存到数据库
        AccountUtil.login(user)
    }

    override fun onCleared() {
        super.onCleared()
        loadingObserver.cancelRequest()
        loadingObserverRegister.cancelRequest()
        loadingObserverLogout.cancelRequest()
    }
}