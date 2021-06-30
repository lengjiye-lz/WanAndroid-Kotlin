package com.lengjiye.code.todo.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.todo.bean.TodoData
import com.lengjiye.code.todo.model.TodoModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @Author: lz
 * @Date: 2020-05-25
 * @Description:
 */
class AddTodoViewModel(application: Application) : BaseViewModel(application) {

    // 操作结果 成功true 失败false
    var operateResult = MutableLiveData<Boolean>()

    fun addTodo(title: String, content: String, date: String, type: Int? = 0) {
        TodoModel.singleton.addTodo(title, content, date, type)?.onEach {
            operateResult.value = true
        }?.catch {
            operateResult.value = false
        }?.launchIn(viewModelScope)
    }

    fun updateTodo(todoData: TodoData) {
        TodoModel.singleton.updateTodo(todoData)?.onEach {
            operateResult.value = true
        }?.catch {
            operateResult.value = false
        }?.launchIn(viewModelScope)
    }
}
