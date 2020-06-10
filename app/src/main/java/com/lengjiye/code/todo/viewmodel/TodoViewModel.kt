package com.lengjiye.code.todo.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.todo.bean.TodoBean
import com.lengjiye.code.todo.bean.TodoData
import com.lengjiye.code.todo.model.TodoModel
import com.lengjiye.network.LoadingObserver
import com.lengjiye.network.exception.ApiException

/**
 * @Author: lz
 * @Date: 2020-05-25
 * @Description:
 */
class TodoViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserver: LoadingObserver<TodoBean>
    private lateinit var loadingObserverUpdateDone: LoadingObserver<String>
    private lateinit var loadingObserverDelete: LoadingObserver<String>

    val todoBean = MutableLiveData<TodoBean>()


    override fun onCreate() {
        loadingObserver = LoadingObserver(object : LoadingObserver.ObserverListener<TodoBean>() {
            override fun observerOnNext(data: TodoBean?) {
                super.observerOnNext(data)
                todoBean.value = data
            }

            override fun observerOnError(e: ApiException) {
                super.observerOnError(e)
            }
        })


        loadingObserverUpdateDone = LoadingObserver(object : LoadingObserver.ObserverListener<String>() {
            override fun observerOnNext(data: String?) {
                super.observerOnNext(data)
            }

            override fun observerOnError(e: ApiException) {
                super.observerOnError(e)
            }
        })
        loadingObserverDelete = LoadingObserver(object : LoadingObserver.ObserverListener<String>() {
            override fun observerOnNext(data: String?) {
                super.observerOnNext(data)
            }

            override fun observerOnError(e: ApiException) {
                super.observerOnError(e)
            }
        })
    }

    fun getTodoList(page: Int, status: Int, type: Int) {
        loadingObserver.cancelRequest()
        TodoModel.singleton.getTodoList(page, status, type, loadingObserver)
    }


    fun updateDoneTodo(id: Int, status: Int) {
        loadingObserverUpdateDone.cancelRequest()
        TodoModel.singleton.updateDoneTodo(id, status, loadingObserverUpdateDone)
    }

    fun deleteTodo(id: Int) {
        loadingObserverDelete.cancelRequest()
        TodoModel.singleton.deleteTodo(id, loadingObserverDelete)
    }

    override fun onCleared() {
        super.onCleared()
        loadingObserver.cancelRequest()
        loadingObserverUpdateDone.cancelRequest()
        loadingObserverDelete.cancelRequest()
    }
}
