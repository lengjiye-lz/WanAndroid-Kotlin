package com.lengjiye.code.utils

import com.lengjiye.code.constant.SPKey
import com.lengjiye.code.me.bean.UserBean
import com.lengjiye.tools.SPTool

/**
 * 账户相关
 */
object AccountUtil {

    /**
     * 是否登录
     */
    fun isLogin(): Boolean {
        return getUserId() != 0
    }

    /**
     * 是否登录
     */
    fun isNoLogin(): Boolean {
        return !isLogin()
    }

    fun getUserName(): String {
        return SPTool.getString(SPKey.KEY_NAME, SPKey.KEY_USER_NAME, "")
    }


    fun getUserId(): Int {
        return SPTool.getInt(SPKey.KEY_NAME, SPKey.KEY_USER_ID, 0)
    }

    fun getNickName(): String {
        return SPTool.getString(SPKey.KEY_NAME, SPKey.KEY_NICK_NAME, "")
    }

    fun setUserName(name: String) {
        SPTool.putString(SPKey.KEY_NAME, SPKey.KEY_USER_NAME, name)
    }

    /**
     * 退出
     */
    fun logout() {
        SPTool.clear(SPKey.KEY_NAME)
    }

    fun login(userBean: UserBean) {
        SPTool.putInt(SPKey.KEY_NAME, SPKey.KEY_USER_ID, userBean.id)
        SPTool.putString(SPKey.KEY_NAME, SPKey.KEY_NICK_NAME, userBean.nickname)
        SPTool.putString(SPKey.KEY_NAME, SPKey.KEY_USER_NAME, userBean.username)
    }

}