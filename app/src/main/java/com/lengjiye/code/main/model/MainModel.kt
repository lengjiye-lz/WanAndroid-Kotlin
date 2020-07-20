package com.lengjiye.code.main.model

import com.lengjiye.code.home.bean.HotKey
import com.lengjiye.code.main.manager.RetrofitCoroutineDSL
import com.lengjiye.code.main.serve.MainServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
import com.lengjiye.network.exception.ErrorCodeConstant
import io.reactivex.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = MainModel()
    }

    private fun getServe(): MainServe? {
        return ServeHolder.singleton.getServe(MainServe::class.java)
    }

    fun getHotKeyList(observer: Observer<List<HotKey>>) {
        val observable = getServe()?.getHotKeyList()?.map(HttpResultFunc())
        observable?.let { makeSubscribe(it, observer) }
    }


    fun getHotKeyList1(coroutineScope: CoroutineScope, observer: RetrofitCoroutineDSL<List<HotKey>>.() -> Unit) {
        retrofit<List<HotKey>>(coroutineScope) {
            api = getServe()?.getHotKeyList1()
            apply(observer)
        }
    }


    fun <T> retrofit(coroutineScope: CoroutineScope, observer: RetrofitCoroutineDSL<T>.() -> Unit) {
        coroutineScope.launch {
            val coroutine = RetrofitCoroutineDSL<T>().apply(observer)
            coroutine.api?.let { call ->
                val deferred = async(Dispatchers.IO) {
                    try {
                        call.execute()
                    } catch (e: Exception) {
                        coroutine.onFail?.invoke("网络错误:${e.message}", -1)
                        null
                    }
                }
                deferred.invokeOnCompletion {
                    if (deferred.isCancelled) {
                        call.cancel()
                        coroutine.clean()
                    }
                }

                val response = deferred.await()
                if (response == null) {
                    coroutine.onFail?.invoke("返回空", -2)
                } else {
                    if (response.isSuccessful) {
                        if (response.body()?.errorCode == ErrorCodeConstant.REQUEST_SUCCESS) {
                            coroutine.onSuccess?.invoke(response.body()?.data!!)
                        } else {
                            coroutine.onFail?.invoke(response.body()?.errorMsg ?: "", -3)
                        }
                    } else {
                        coroutine.onFail?.invoke("请求失败", -3)
                    }
                }
            }
        }
    }
}
