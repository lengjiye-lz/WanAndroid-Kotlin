package com.lengjiye.network

import com.google.gson.annotations.SerializedName

class BaseHttpResult<T> {

    @SerializedName(value = "statusCode", alternate = ["code", "errcode"])
    var errorCode: Int? = null

    @SerializedName(value = "msg", alternate = ["errmsg"])
    var errorMsg: String? = null

    var data: T? = null

}