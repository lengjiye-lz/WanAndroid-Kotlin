package com.lengjiye.utils

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object IoUtil {


    /**
     * 循环
     */
    fun interval(period: Long, succ: (long: Long) -> Unit): Disposable? {
        return interval(0, period)
            .subscribe {
                succ.invoke(it)
            }
    }

    /**
     * 循环
     */
    fun interval(initialDelay: Long, period: Long, succ: (long: Long) -> Unit): Disposable? {
        return interval(initialDelay, period)
            .subscribe {
                succ.invoke(it)
            }
    }

    /**
     * 循环任务
     */
    fun interval(initialDelay: Long, period: Long): Observable<Long> {
        return Observable.interval(initialDelay, period, TimeUnit.SECONDS)
            .compose(io())
    }

    /**
     * 定时任务
     *
     * 只执行一次
     */
    fun timer(delay: Long, listener: (long: Long) -> Unit): Disposable? {
        return Observable.timer(delay, TimeUnit.SECONDS)
            .compose(io()).subscribe(listener)
    }

    /**
     * 定时任务
     *
     * 只执行一次
     */
    fun timer(delay: Long): Observable<Long> {
        return Observable.timer(delay, TimeUnit.SECONDS)
            .compose(io())
    }

    /**
     * io 线程切换
     */
    fun <T> io(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * newThread 线程切换
     */
    fun <T> newThread(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 线程池  线程切换
     */
    fun <T> computation(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}