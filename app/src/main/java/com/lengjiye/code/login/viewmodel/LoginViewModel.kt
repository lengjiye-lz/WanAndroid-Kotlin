package com.lengjiye.code.login.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.login.model.LoginModel
import com.lengjiye.code.me.bean.UserBean
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

    override fun onCreate() {
        loadingObserver = LoadingObserver(object : LoadingObserver.ObserverListener<UserBean> {
            override fun observerOnNext(data: UserBean?) {

            }

            override fun observerOnError(e: ApiException) {

            }

        })

        loadingObserverRegister = LoadingObserver(object : LoadingObserver.ObserverListener<UserBean> {
            override fun observerOnNext(data: UserBean?) {

            }

            override fun observerOnError(e: ApiException) {

            }

        })

        loadingObserverLogout = LoadingObserver(object : LoadingObserver.ObserverListener<String> {
            override fun observerOnNext(data: String?) {

            }

            override fun observerOnError(e: ApiException) {

            }

        })
    }

    fun login(lifecycleOwner: LifecycleOwner, username: String, password: String) {
        loadingObserver.cancelRequest()
        LoginModel.singleton.login(lifecycleOwner, username, password, loadingObserver)
    }

    fun register(lifecycleOwner: LifecycleOwner, username: String, password: String, repassword: String) {
        loadingObserverRegister.cancelRequest()
        LoginModel.singleton.register(lifecycleOwner, username, password, repassword, loadingObserverRegister)
    }

    fun logout(lifecycleOwner: LifecycleOwner) {
        loadingObserverLogout.cancelRequest()
        LoginModel.singleton.logout(lifecycleOwner, loadingObserverLogout)
    }

    override fun onDestroy() {
        loadingObserver.cancelRequest()
        loadingObserverRegister.cancelRequest()
        loadingObserverLogout.cancelRequest()
    }
}