package com.lengjiye.code.networkscope

class BaseHttpResult<T> {

    var errorCode: Int? = null

    var errorMsg: String? = null

    var data: T? = null
}