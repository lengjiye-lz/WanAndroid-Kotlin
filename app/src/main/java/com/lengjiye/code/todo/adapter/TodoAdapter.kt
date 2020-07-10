package com.lengjiye.code.todo.adapter

import android.content.Context
import android.view.View
import com.lengjiye.base.recycleview.BaseDBAdapter
import com.lengjiye.base.recycleview.BaseDBViewHolder
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ItemTodoBinding
import com.lengjiye.code.todo.bean.TodoData
import com.lengjiye.tools.ResTool

/**
 * 文章列表适配器
 */
class TodoAdapter constructor(val context: Context, models: MutableList<TodoData>?) :
    BaseDBAdapter<TodoData, TodoAdapter.TodoHolderDB, ItemTodoBinding>(context, models) {

    override fun onBindViewHolder(holder: TodoHolderDB, position: Int, item: TodoData?) {
        item?.let {
            holder.binding.tvTitle.text = ResTool.fromHtml(it.title).trim()
            holder.binding.tvContent.text = ResTool.fromHtml(it.content).trim()
            it.completeDateStr?.let { time ->
                holder.binding.tvTime.visibility = View.VISIBLE
                holder.binding.tvTime.text = time
            }

            holder.binding.btnDelete.setOnClickListener {
                holder.itemView.scrollTo(0, 0)
                getItems().removeAt(position)
                notifyItemRemoved(position)
                notifyDataSetChanged()
            }
        }
    }

    class TodoHolderDB(binding: ItemTodoBinding) : BaseDBViewHolder<ItemTodoBinding>(binding)

    override fun getLayoutId(): Int {
        return R.layout.item_todo
    }

    override fun getViewHolder(binding: ItemTodoBinding): TodoHolderDB {
        return TodoHolderDB(binding)
    }
}