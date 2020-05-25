package com.lengjiye.code.todo.fragment

import com.lengjiye.base.fragment.ParentFragment
import com.lengjiye.code.R
import com.lengjiye.code.databinding.FragmentTodoBinding
import com.lengjiye.code.todo.viewmodel.TodoViewModel

/**
 * @Author: lz
 * @Date: 2020-05-25
 * @Description:
 */
class TodoFragment : ParentFragment<FragmentTodoBinding, TodoViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_todo
    }
}