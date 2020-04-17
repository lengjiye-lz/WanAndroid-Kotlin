package com.lengjiye.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object RxUtil {

    /**
     * 循环
     */
    fun interval(lifecycleOwner: LifecycleOwner?, period: Long, succ: (long: Long) -> Unit): Disposable? {
        return interval(lifecycleOwner, 0, period)
            .subscribe {
                succ.invoke(it)
            }
    }

    /**
     * 循环
     */
    fun interval(
        lifecycleOwner: LifecycleOwner?, initialDelay: Long, period: Long, succ: (long: Long) -> Unit
    ): Disposable? {
        return interval(lifecycleOwner, initialDelay, period)
            .subscribe {
                succ.invoke(it)
            }
    }

    /**
     * 循环任务
     */
    fun interval(lifecycleOwner: LifecycleOwner?, initialDelay: Long, period: Long): Observable<Long> {
        return Observable.interval(initialDelay, period, TimeUnit.SECONDS)
            .compose(ioScheduler(lifecycleOwner))


    }

    /**
     * 定时任务
     *
     * 只执行一次
     */
    fun timer(lifecycleOwner: LifecycleOwner?, delay: Long, listener: (long: Long) -> Unit): Disposable? {
        return Observable.timer(delay, TimeUnit.SECONDS)
            .compose(ioScheduler(lifecycleOwner)).subscribe(listener)
    }

    /**
     * 定时任务
     *
     * 只执行一次
     */
    fun timer(lifecycleOwner: LifecycleOwner?, delay: Long): Observable<Long> {
        return Observable.timer(delay, TimeUnit.SECONDS)
            .compose(ioScheduler(lifecycleOwner))
    }

    /**
     * 执行单个任务
     */
    fun just(lifecycleOwner: LifecycleOwner?, string: String = "", listener: (long: String) -> Unit): Disposable? {
        return Observable.just(string).compose(ioScheduler(lifecycleOwner)).subscribe(listener)
    }

    /**
     * 执行单个任务,可以延迟
     */
    fun <T> create(lifecycleOwner: LifecycleOwner?, task: RXSimpleTask<T>, delay: Long = 0): Disposable? {
        return Observable.create<T> {
            val t = task.doSth()
            if (t == null) {
                it.onError(Throwable("is null"))
            } else {
                it.onNext(t)
            }
            it.onComplete()
        }.compose(ioScheduler(lifecycleOwner))
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
    fun <T> ioScheduler(lifecycleOwner: LifecycleOwner?): ObservableTransformer<T, T> {
        return ObservableTransformer {
            lifecycleOwner?.let { _ ->
                it.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY))
            }
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * newThread 线程切换
     */
    fun <T> newThreadScheduler(lifecycleOwner: LifecycleOwner?): ObservableTransformer<T, T> {
        return ObservableTransformer {
            lifecycleOwner?.let { _ ->
                it.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY))
            }
            it.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 线程池  线程切换
     */
    fun <T> computationScheduler(lifecycleOwner: LifecycleOwner?): ObservableTransformer<T, T> {
        return ObservableTransformer {
            lifecycleOwner?.let { _ ->
                it.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY))
            }
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