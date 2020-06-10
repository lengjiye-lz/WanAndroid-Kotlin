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
class AddTodoViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var loadingObserverAdd: LoadingObserver<String>
    private lateinit var loadingObserverUpdate: LoadingObserver<String>

    // 操作结果 成功true 失败false
    var operateResult = MutableLiveData<Boolean>()

    override fun onCreate() {
        loadingObserverAdd = LoadingObserver(object : LoadingObserver.ObserverListener<String>() {
            override fun observerOnNext(data: String?) {
                super.observerOnNext(data)
                operateResult.value = true
            }

            override fun observerOnError(e: ApiException) {
                super.observerOnError(e)
                operateResult.value = false
            }
        })
        loadingObserverUpdate = LoadingObserver(object : LoadingObserver.ObserverListener<String>() {
            override fun observerOnNext(data: String?) {
                super.observerOnNext(data)
                operateResult.value = true
            }

            override fun observerOnError(e: ApiException) {
                super.observerOnError(e)
                operateResult.value = false
            }
        })
    }

    fun addTodo(title: String, content: String, date: String, type: Int? = 0) {
        loadingObserverAdd.cancelRequest()
        TodoModel.singleton.addTodo(title, content, date, type, loadingObserverAdd)
    }

    fun updateTodo(todoData: TodoData) {
        loadingObserverUpdate.cancelRequest()
        TodoModel.singleton.updateTodo(todoData, loadingObserverUpdate)
    }

    override fun onCleared() {
        super.onCleared()
        loadingObserverAdd.cancelRequest()
        loadingObserverUpdate.cancelRequest()
    }
}
