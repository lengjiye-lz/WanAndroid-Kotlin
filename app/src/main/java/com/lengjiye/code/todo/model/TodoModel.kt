package com.lengjiye.code.todo.model

import com.lengjiye.code.constant.NetWorkParams
import com.lengjiye.code.todo.bean.TodoBean
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
        type?.let {
            map[NetWorkParams.TYPE] = it.toString()
        }
        map[NetWorkParams.PRIORITY] = "1"
        val observable = getServe()?.addTodo(map)?.map(HttpResultFunc())
        makeSubscribe(observable, observer)
    }

    fun updateTodo(id: Int, title: String, content: String, date: String, status: Int?, type: Int?, observer: Observer<String>) {
        val map = hashMapOf<String, String>()
        map[NetWorkParams.TITLE] = title
        map[NetWorkParams.CONTENT] = content
        map[NetWorkParams.DATE] = date
        status?.let {
            map[NetWorkParams.STATUS] = it.toString()
        }
        type?.let {
            map[NetWorkParams.TYPE] = it.toString()
        }
        map[NetWorkParams.PRIORITY] = "1"
        val observable = getServe()?.updateTodo(id, map)?.map(HttpResultFunc())
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
