package com.lengjiye.code.todo.activity

import android.annotation.SuppressLint
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityTodoBinding
import com.lengjiye.code.todo.viewmodel.TodoViewModel

/**
 * @Author: lz
 * @Date: 2020-05-25
 * @Description:
 */
class TodoActivity : BaseActivity<ActivityTodoBinding, TodoViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_todo
    }
}
