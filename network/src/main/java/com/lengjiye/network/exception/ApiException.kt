package com.lengjiye.network.exception

import java.lang.RuntimeException

class ApiException : RuntimeException {
    var mErrorCode: Int? = null
    var mErrorMsg: String? = null
    var mData: Any? = null

    constructor(mErrorCode: Int?, mErrorMsg: String?, mData: Any?) : super(mErrorMsg) {
        this.mErrorCode = mErrorCode
        this.mErrorMsg = mErrorMsg
        this.mData = mData
    }
}