package com.lengjiye.network

import com.google.gson.Gson
import com.lengjiye.network.exception.ApiException
import com.lengjiye.network.exception.ErrorCodeConstant
import kotlinx.coroutines.flow.*
import java.lang.Exception

/**
 * 统一处理Http请求中，如果errorCode不为0，则抛出Api异常
 * 如果errorCode为0，返回data部分数据
 */
//class FlowHttpResultFunc<T> : Function<BaseHttpResult<T>, T> {
//
//    override fun apply(t: BaseHttpResult<T>): T? {
//        val errorCode = t.errorCode
//        if (errorCode == ErrorCodeConstant.REQUEST_SUCCESS) {
//            return t.data
//        } else {
//            var originalData = ""
//            try {
//                originalData = Gson().toJson(t)
//            } catch (e: Exception) {
//            }
//            throw ApiException(errorCode, t.errorMsg, originalData)
//        }
//    }
//
//}


fun <T> Flow<BaseHttpResult<T>>.transform(): Flow<T?> {
    return catch {
        throw ApiException(-1, it.message, "") }.flatMapConcat {
        processResponse(it.errorCode, it.errorMsg, it.data)
    }.map { it }
}

private fun <T> processResponse(code: Int?, message: String?, value: T?) = flow {
    if (code == ErrorCodeConstant.REQUEST_SUCCESS) {
        emit(value)
    } else {
        var originalData = ""
        try {
            originalData = Gson().toJson(value)
        } catch (e: Exception) {
        }
        throw ApiException(code, message, originalData)
    }
}