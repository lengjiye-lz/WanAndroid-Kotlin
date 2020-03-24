package com.lengjiye.network.exception

import java.lang.RuntimeException

class ApiException(var mErrorCode: Int?, var mErrorMsg: String?, var mData: Any?) : RuntimeException(mErrorMsg) {

}