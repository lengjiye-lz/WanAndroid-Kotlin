package com.lengjiye.network

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.android.lifecycle.autoDisposable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class BaseModel {

    /**
     * 控制线程，并发起订阅
     *
     * @param observable
     * @param observer
     * @param <T>
    </T> */
    fun <T> makeSubscribe(lifecycleOwner: LifecycleOwner, observable: Observable<T>, observer: Observer<T>?) {
        observable.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(lifecycleOwner, Lifecycle.Event.ON_DESTROY) // 防止 rxJava 内存泄漏
            .subscribe(observer)
    }
}