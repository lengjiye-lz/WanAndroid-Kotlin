package com.lengjiye.network


open class BaseModel {

//    /**
//     * 控制线程，并发起订阅
//     *
//     * @param observable
//     * @param observer
//     * @param <T>
//    </T> */
//    fun <T> makeSubscribe(observable: Observable<T>?, observer: Observer<T>?) {
//        observer?.let {
//            observable?.subscribeOn(Schedulers.io())
//                ?.unsubscribeOn(Schedulers.io())
//                ?.observeOn(AndroidSchedulers.mainThread(),true)
//                //            .autoDisposable(lifecycleOwner, Lifecycle.Event.ON_DESTROY) // 防止 rxJava 内存泄漏
//                ?.subscribe(it)
//        }
//    }
}