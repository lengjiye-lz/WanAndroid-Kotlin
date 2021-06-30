package com.lengjiye.code.login.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lengjiye.base.constant.ErrorCode
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.login.model.LoginModel
import com.lengjiye.code.me.bean.UserBean
import com.lengjiye.code.utils.AccountUtils
import com.lengjiye.network.exception.ApiException
import com.lengjiye.tools.ResTool
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class LoginViewModel(application: Application) : BaseViewModel(application) {
    var loginSuc = MutableLiveData<Boolean>()
    var registerSuc = MutableLiveData<Boolean>()

    var logoutSuc = MutableLiveData<Boolean>()

    /**
     * 登录
     */
    fun login(username: String?, password: String?) {
        if (username.isNullOrEmpty()) {
            errorCode.value = ApiException(ErrorCode.nameError, ResTool.getString(R.string.s_9), null)
            return
        }
        AccountUtils.setUserName(username)
        if (password.isNullOrEmpty()) {
            errorCode.value = ApiException(ErrorCode.passError, ResTool.getString(R.string.s_10), null)
            return
        }
        LoginModel.singleton.login(username, password)?.onEach {
            it?.let {
                saveAccount(it)
                loginSuc.value = true
            }
        }?.catch {
            errorCode.value = it
            loginSuc.value = false
        }?.launchIn(viewModelScope)
    }

    /**
     * 注册
     */
    fun register(username: String?, password: String?, rePassword: String?) {
        if (username.isNullOrEmpty()) {
            errorCode.value = ApiException(ErrorCode.nameError, ResTool.getString(R.string.s_9), null)
            return
        }
        AccountUtils.setUserName(username)
        if (password.isNullOrEmpty()) {
            errorCode.value = ApiException(ErrorCode.passError, ResTool.getString(R.string.s_10), null)
            return
        }

        if (rePassword.isNullOrEmpty()) {
            errorCode.value = ApiException(ErrorCode.rePassError, ResTool.getString(R.string.s_11), null)
            return
        }
        LoginModel.singleton.register(username, password, rePassword)?.onEach {
            it?.let {
                saveAccount(it)
                registerSuc.value = true
            }
        }?.catch {

        }?.launchIn(viewModelScope)
    }

    /**
     * 登出
     */
    fun logout() {
        LoginModel.singleton.logout()?.onEach {  }?.catch {  }?.launchIn(viewModelScope)
    }

    /**
     * 保存账号
     */
    private fun saveAccount(user: UserBean) {
        // TODO  保存到数据库
        AccountUtils.login(user)
    }
}