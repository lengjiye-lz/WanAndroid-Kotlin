package com.lengjiye.utils

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object RxUtil {

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
    fun interval(initialDelay: Long, period: Long, succ: (long: Long) -> Unit
    ): Disposable? {
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
            .compose(ioScheduler())
    }

    /**
     * 定时任务
     *
     * 只执行一次
     */
    fun timer(delay: Long, listener: (long: Long) -> Unit): Disposable? {
        return Observable.timer(delay, TimeUnit.SECONDS)
            .compose(ioScheduler()).subscribe(listener)
    }

    /**
     * 定时任务
     *
     * 只执行一次
     */
    fun timer(delay: Long): Observable<Long> {
        return Observable.timer(delay, TimeUnit.SECONDS)
            .compose(ioScheduler())
    }

    /**
     * 执行单个任务
     */
    fun just(string: String = "", listener: (long: String) -> Unit): Disposable? {
        return Observable.just(string).compose(ioScheduler()).subscribe(listener)
    }

    /**
     * 执行单个任务,可以延迟
     */
    fun <T> create(task: RXSimpleTask<T>, delay: Long = 0): Disposable? {
        return Observable.create<T> {
            val t = task.doSth()
            if (t == null) {
                it.onError(Throwable("is null"))
            } else {
                it.onNext(t)
            }
            it.onComplete()
        }.compose(ioScheduler())
            .delay(delay, TimeUnit.MILLISECONDS)
            .subscribe({
                task.onNext(it)
            }, {
                task.onError(it)
            }, {
                task.onComplete()
            })
    }

    /**
     * io 线程切换
     */
    fun <T> ioScheduler(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * newThread 线程切换
     */
    fun <T> newThreadScheduler(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 线程池  线程切换
     */
    fun <T> computationScheduler(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    abstract class RXSimpleTask<T> {
        open fun doSth(): T? {
            return null
        }

        open fun onNext(data: T) {}

        open fun onError(e: Throwable) {}

        open fun onComplete() {}
    }
}