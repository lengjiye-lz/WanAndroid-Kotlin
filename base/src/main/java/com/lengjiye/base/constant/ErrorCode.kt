package com.lengjiye.base.constant

/**
 * errorCode
 * 主要用于请求接口错误处理，使用MutableLiveData进行通知，便于管理
 * 每个接口的错误码不能相同
 */
object ErrorCode {
    // 登录失败
    const val loginError = 101
    // 名字错误
    const val nameError = 102
    // 密码错误
    const val passError = 103
    // 两次密码不同
    const val rePassError = 104
}