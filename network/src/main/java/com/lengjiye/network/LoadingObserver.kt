package com.lengjiye.network

import com.lengjiye.tools.NetWorkTool
import com.lengjiye.tools.ResTool
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 请求dialog统一处理
 *
 * code 拦截
 */
class LoadingObserver<T> : Observer<T> {

    private var disposable: Disposable? = null

    private var errorMsg: String? = null
    private var errorCode: Int? = null

    override fun onSubscribe(d: Disposable) {
        disposable = d
        if (!NetWorkTool.isNetWorkConnected()) {
            errorCode = ErrorCodeConstant.NO_NETWORK
            errorMsg = ResTool.getString(R.string.net_0001)
        }
    }

    override fun onNext(t: T) {
    }

    override fun onComplete() {
    }


    override fun onError(e: Throwable) {
    }


}