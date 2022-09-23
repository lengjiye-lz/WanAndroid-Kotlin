package com.lengjiye.network

class BaseHttpResult<T> {

    var errorCode: Int? = null

    var errorMsg: String? = null

    var data: T? = null
}