package com.lengjiye.code.todo.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.todo.bean.TodoBean
import com.lengjiye.code.todo.model.TodoModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @Author: lz
 * @Date: 2020-05-25
 * @Description:
 */
class TodoViewModel(application: Application) : BaseViewModel(application) {

    val todoBean = MutableLiveData<TodoBean>()

    fun getTodoList(page: Int, status: Int, type: Int) {
        TodoModel.singleton.getTodoList(page, status, type)?.onEach {
            todoBean.value = it
        }?.catch {

        }?.launchIn(viewModelScope)
    }


    fun updateDoneTodo(id: Int, status: Int) {
        TodoModel.singleton.updateDoneTodo(id, status)?.onEach {

        }?.catch {

        }?.launchIn(viewModelScope)
    }

    fun deleteTodo(id: Int) {
        TodoModel.singleton.deleteTodo(id)?.onEach {

        }?.catch {

        }?.launchIn(viewModelScope)
    }


}
