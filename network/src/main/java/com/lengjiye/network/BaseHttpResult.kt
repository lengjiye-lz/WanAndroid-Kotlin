package com.lengjiye.network

import com.google.gson.annotations.SerializedName

class BaseHttpResult<T> {

    @SerializedName(value = "statusCode", alternate = ["code", "errcode"])
    var errcode: Int? = null

    @SerializedName(value = "msg", alternate = ["errmsg"])
    var errmsg: String? = null

    var data: T? = null

}