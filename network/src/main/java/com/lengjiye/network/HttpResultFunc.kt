package com.lengjiye.network

import com.google.gson.Gson
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.exception.ErrorCodeConstant
import io.reactivex.functions.Function
import java.lang.Exception

/**
 * 统一处理Http请求中，如果errorCode不为0，则抛出Api异常
 * 如果errorCode为0，返回data部分数据
 */
class HttpResultFunc<T> : Function<BaseHttpResult<T>, T> {

    override fun apply(t: BaseHttpResult<T>): T? {
        val errorCode = t.errorCode
        if (errorCode == ErrorCodeConstant.REQUEST_SUCCESS) {
            return t.data
        } else {
            var originalData = ""
            try {
                originalData = Gson().toJson(t)
            } catch (e: Exception) {
            }
            throw ApiException(errorCode, t.errorMsg, originalData)
        }
    }

}