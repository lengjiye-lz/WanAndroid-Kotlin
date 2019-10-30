package com.lengjiye.base.recycleview

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView

open class HeaderAndFooterWrapper<T>(val adapter: BaseAdapter<T, RecyclerView.ViewHolder>) : BaseAdapter<T, RecyclerView.ViewHolder>() {

    private val BASE_ITEM_TYPE_HEADER = 100000
    private val BASE_ITEM_TYPE_FOOTER = 200000

    private val mHeaderViews = SparseArrayCompat<View>()
    private val mFootViews = SparseArrayCompat<View>()

    override fun getItemViewType(position: Int): Int {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position)
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount())
        }
        return adapter.getItemViewType(position - getHeadersCount())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (mHeaderViews.get(viewType) != null) {
            return BaseViewHolder(mHeaderViews.get(viewType)!!)
        } else if (mFootViews.get(viewType) != null) {
            return BaseViewHolder(mFootViews.get(viewType)!!)
        }
        return adapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isHeaderViewPos(position)) {
            return
        }
        if (isFooterViewPos(position)) {
            return
        }
        adapter.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return getHeadersCount() + getFootersCount() + getRealItemCount()
    }

    private fun getRealItemCount(): Int {
        return adapter.itemCount
    }

    private fun isHeaderViewPos(position: Int): Boolean {
        return position < getHeadersCount()
    }

    private fun isFooterViewPos(position: Int): Boolean {
        return position >= getHeadersCount() + getRealItemCount()
    }

    fun addHeaderView(view: View) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view)
        adapter.notifyDataSetChanged()
    }

    fun addFootView(view: View) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view)
        adapter.notifyDataSetChanged()
    }

    fun removeAllHeaderView() {
        mHeaderViews.clear()
    }

    override fun getHeadersCount(): Int {
        return mHeaderViews.size()
    }

    fun getFootersCount(): Int {
        return mFootViews.size()
    }
}