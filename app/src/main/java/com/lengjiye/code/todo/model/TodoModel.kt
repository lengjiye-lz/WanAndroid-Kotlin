package com.lengjiye.code.todo.model

import com.lengjiye.code.todo.serve.TodoServe
import com.lengjiye.network.BaseModel
import com.lengjiye.network.ServeHolder

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
}
