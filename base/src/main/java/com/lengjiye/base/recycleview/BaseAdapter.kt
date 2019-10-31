package com.lengjiye.base.recycleview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder>() : RecyclerView.Adapter<VH>() {

    constructor(mContext: Context, items: MutableList<T>?) : this() {
        this.mContext = mContext
        items?.let { mItems.addAll(it) }
        mLayoutInflater = LayoutInflater.from(mContext)
    }

    private var mContext: Context? = null

    protected lateinit var mLayoutInflater: LayoutInflater
    private val mItems: MutableList<T> = ArrayList()


    override fun getItemCount(): Int {
        return mItems.size
    }

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
            notifyItemRangeInserted(itemCount, it.size)
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
