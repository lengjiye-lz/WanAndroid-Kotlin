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
    private lateinit var loadingObserverAdd: LoadingObserver<String>
    private lateinit var loadingObserverUpdate: LoadingObserver<String>
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

        loadingObserverAdd = LoadingObserver(object : LoadingObserver.ObserverListener<String>() {
            override fun observerOnNext(data: String?) {
                super.observerOnNext(data)
            }

            override fun observerOnError(e: ApiException) {
                super.observerOnError(e)
            }
        })
        loadingObserverUpdate = LoadingObserver(object : LoadingObserver.ObserverListener<String>() {
            override fun observerOnNext(data: String?) {
                super.observerOnNext(data)
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

    fun addTodo(title: String, content: String, date: String, type: Int?) {
        loadingObserverAdd.cancelRequest()
        TodoModel.singleton.addTodo(title, content, date, type, loadingObserverAdd)
    }

    fun updateTodo(id: Int, title: String, content: String, date: String, status: Int?, type: Int?) {
        loadingObserverUpdate.cancelRequest()
        TodoModel.singleton.updateTodo(id, title, content, date, status, type, loadingObserverUpdate)
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
        loadingObserverAdd.cancelRequest()
        loadingObserverUpdate.cancelRequest()
        loadingObserverUpdateDone.cancelRequest()
        loadingObserverDelete.cancelRequest()
    }
}
