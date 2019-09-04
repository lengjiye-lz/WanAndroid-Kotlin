package com.lengjiye.network

import com.google.gson.Gson
import io.reactivex.functions.Function
import java.lang.Exception

class HttpResultFunc<T> : Function<BaseHttpResult<T>, T> {

    override fun apply(t: BaseHttpResult<T>): T? {
        var errorCode = t.errcode
        return if (errorCode == 0) {
            t.data
        } else {
            var originalData = ""
            try {
                originalData = Gson().toJson(t)
            } catch (e: Exception) {
            }
            ApiException(errorCode, t.errmsg, originalData) as T
        }
    }

}