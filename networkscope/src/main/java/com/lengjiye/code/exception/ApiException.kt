package com.lengjiye.code.exception

import java.lang.RuntimeException

class Api1Exception(var mErrorCode: Int?, var mErrorMsg: String?) : RuntimeException(mErrorMsg) {

}