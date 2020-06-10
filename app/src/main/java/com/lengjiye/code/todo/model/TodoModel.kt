package com.lengjiye.code.todo.model

import com.lengjiye.code.constant.NetWorkParams
import com.lengjiye.code.todo.bean.TodoBean
import com.lengjiye.code.todo.bean.TodoData
import com.lengjiye.code.todo.serve.TodoServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServeHolder
import io.reactivex.Observer

/**
 * @Author: lz
 * @Date: 2020-05-25
 * @Description:
 */
class TodoModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = TodoModel()
    }

    private fun getServe(): TodoServe? {
        return ServeHolder.singleton.getServe(TodoServe::class.java)
    }

    fun getTodoList(page: Int, status: Int, type: Int, observer: Observer<TodoBean>) {
        val map = hashMapOf<String, String>()
        map[NetWorkParams.STATUS] = "$status"
        map[NetWorkParams.TYPE] = "$type"
        if (status == 1) map[NetWorkParams.ORDER_BY] = "2" // 按完成时间逆序
        else map[NetWorkParams.ORDER_BY] = "4" // 按创建时间逆序
        val observable = getServe()?.getTodoList(page, map)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun addTodo(title: String, content: String, date: String, type: Int?, observer: Observer<String>) {
        val map = hashMapOf<String, String>()
        map[NetWorkParams.TITLE] = title
        map[NetWorkParams.CONTENT] = content
        map[NetWorkParams.DATE] = date
        map[NetWorkParams.TYPE] = (type ?: 0).toString()
        map[NetWorkParams.PRIORITY] = "2"
        val observable = getServe()?.addTodo(map)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun updateTodo(todoData: TodoData, observer: Observer<String>) {
        val map = hashMapOf<String, String>()
        map[NetWorkParams.TITLE] = todoData.title
        map[NetWorkParams.CONTENT] = todoData.content
        map[NetWorkParams.DATE] = todoData.dateStr
        map[NetWorkParams.STATUS] = todoData.status.toString()
        map[NetWorkParams.TYPE] = todoData.type.toString()
        map[NetWorkParams.PRIORITY] = "2"
        val observable = getServe()?.updateTodo(todoData.id, map)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun updateDoneTodo(id: Int, status: Int, observer: Observer<String>) {
        val observable = getServe()?.updateDoneTodo(id, status)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun deleteTodo(id: Int, observer: Observer<String>) {
        val observable = getServe()?.deleteTodo(id)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }
}
