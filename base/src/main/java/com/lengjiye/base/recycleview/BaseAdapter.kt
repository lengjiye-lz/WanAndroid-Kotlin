package com.lengjiye.base.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : BaseViewHolder<*>>(mContext: Context, items: MutableList<T>?) :
    RecyclerView.Adapter<VH>() {
    protected var mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)
    private val mItems: MutableList<T> = ArrayList()
    private var mOnItemClickListener: (v: View, position: Int) -> Unit = { _, _ -> }

    private var onItemClickListener = { v: View ->
        mOnItemClickListener.invoke(v, v.tag as Int)
    }

    init {
        items?.let { mItems.addAll(it) }
    }

    fun setOnItemClickListener(onItemClickListener: (v: View, position: Int) -> Unit) {
        this.mOnItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.tag = position
        holder.itemView.isSoundEffectsEnabled = false
        val item = getItem(position)

        holder.itemView.setOnClickListener(onItemClickListener)
        onBindViewHolder(holder, position, item)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    protected abstract fun onBindViewHolder(holder: VH, position: Int, item: T?)

    open fun getItem(index: Int): T? {
        return if (index < mItems.size && index >= 0) mItems[index] else null
    }

    fun getItems(): MutableList<T> {
        return mItems
    }

    fun add(item: T) {
        mItems.add(item)
        notifyItemInserted(mItems.size - 1)
    }

    fun add(index: Int, item: T) {
        if (index > mItems.size || index < 0)
            return
        mItems.add(index, item)
        notifyItemInserted(index)
    }

    fun addAll(items: MutableList<T>?) {
        items?.let {
            mItems.addAll(it)
            notifyItemRangeInserted(mItems.size, it.size)
        }
    }

    fun addAll(index: Int, items: MutableList<T>?) {
        if (items == null || index > items.size || index < 0)
            return
        mItems.addAll(index, items)
        notifyItemRangeInserted(index, items.size)
    }

    fun replace(oldItem: T, newItem: T) {
        val index = mItems.indexOf(oldItem)
        replace(index, newItem)
    }

    fun replace(index: Int, item: T) {
        if (index >= mItems.size || index < 0)
            return
        mItems[index] = item
        notifyItemChanged(index)
    }

    fun replaceAll(items: MutableList<T>?) {
        removeAll()
        addAll(items)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        if (index >= mItems.size || index < 0)
            return
        mItems.removeAt(index)
        notifyItemRemoved(index)
    }

    fun remove(item: T) {
        remove(mItems.indexOf(item))
    }

    fun removeAll(items: MutableList<T>) {
        val removed = mItems.removeAll(items)
        if (removed) {
            notifyDataSetChanged()
        }
    }

    fun removeAll() {
        mItems.clear()
        notifyDataSetChanged()
    }

    operator fun contains(item: T): Boolean {
        return mItems.contains(item)
    }
}
