package com.lengjiye.base.recycleview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseDBAdapter<T, VH : RecyclerView.ViewHolder>(mContext: Context, items: MutableList<T>?) : BaseAdapter<T, VH>(mContext, items) {

    private var mOnItemClickListener: (v: View, position: Int) -> Unit = { _, _ -> }

    private var onItemClickListener = { v: View ->
        mOnItemClickListener.invoke(v, v.tag as Int)
    }

    fun setOnItemClickListener(onItemClickListener: (v: View, position: Int) -> Unit) {
        this.mOnItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.tag = position
        val item = getItem(position)
        holder.itemView.setOnClickListener(onItemClickListener)
        onBindViewHolder(holder, position, item)
    }

    protected abstract fun onBindViewHolder(holder: VH, position: Int, item: T?)

}
