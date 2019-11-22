package com.lengjiye.code.login.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.constant.ErrorCode
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.login.model.LoginModel
import com.lengjiye.code.me.bean.UserBean
import com.lengjiye.code.utils.AccountUtil
import com.lengjiye.network.ApiException
import com.lengjiye.network.LoadingObserver

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

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : LoadingObserver.ObserverListener<UserBean> {
            override fun observerOnNext(data: UserBean?) {
                data?.let {
                    saveAccount(it)
                    loginSuc.value = true
                }
            }

            override fun observerOnError(e: ApiException) {
                errorCode.value = ErrorCode.loginError
            }

        })

        loadingObserverRegister = LoadingObserver(object : LoadingObserver.ObserverListener<UserBean> {
            override fun observerOnNext(data: UserBean?) {
                data?.let {
                    saveAccount(it)
                }
            }

            override fun observerOnError(e: ApiException) {

            }

        })

        loadingObserverLogout = LoadingObserver(object : LoadingObserver.ObserverListener<String> {
            override fun observerOnNext(data: String?) {
                AccountUtil.logout()
            }

            override fun observerOnError(e: ApiException) {

            }
        })
    }

    /**
     * 登录
     */
    fun login(lifecycleOwner: LifecycleOwner, username: String?, password: String?) {
        if (username.isNullOrEmpty()) {
            errorCode.value = ErrorCode.nameError
            return
        }

        if (password.isNullOrEmpty()) {
            errorCode.value = ErrorCode.passError
            return
        }
        loadingObserver.cancelRequest()
        LoginModel.singleton.login(lifecycleOwner, username, password, loadingObserver)
    }

    /**
     * 注册
     */
    fun register(lifecycleOwner: LifecycleOwner, username: String?, password: String?, rePassword: String?) {
        if (username.isNullOrEmpty()) {
            errorCode.value = ErrorCode.nameError
            return
        }

        if (password.isNullOrEmpty()) {
            errorCode.value = ErrorCode.passError
            return
        }

        if (rePassword.isNullOrEmpty()) {
            errorCode.value = ErrorCode.rePassError
            return
        }
        loadingObserverRegister.cancelRequest()
        LoginModel.singleton.register(lifecycleOwner, username, password, rePassword, loadingObserverRegister)
    }

    /**
     * 登出
     */
    fun logout(lifecycleOwner: LifecycleOwner) {
        loadingObserverLogout.cancelRequest()
        LoginModel.singleton.logout(lifecycleOwner, loadingObserverLogout)
    }

    /**
     * 保存账号
     */
    private fun saveAccount(user: UserBean) {
        // TODO  保存到数据库
        AccountUtil.login(user)
    }

    override fun onDestroy() {
        loadingObserver.cancelRequest()
        loadingObserverRegister.cancelRequest()
        loadingObserverLogout.cancelRequest()
    }
}