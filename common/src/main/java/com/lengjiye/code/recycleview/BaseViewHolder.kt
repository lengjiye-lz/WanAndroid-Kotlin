package com.lengjiye.code.recycleview

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mViews: SparseArray<View>? = null

    init {
        mViews = SparseArray()
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    fun <T : View> getView(viewId: Int): T {
        var view: View? = mViews?.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews?.put(viewId, view)
        }
        return view as T
    }

    fun getConvertView(): View {
        return itemView
    }
}
