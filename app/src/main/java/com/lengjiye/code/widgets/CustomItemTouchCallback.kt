package com.lengjiye.code.widgets

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

//class CustomItemTouchCallback(val itemTouchHelper: ItemTo) : ItemTouchHelper.Callback() {
//
//    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
//        // 上下拖动
//        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
//        // 向左滑动
//        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        return makeMovementFlags(dragFlags, swipeFlags)
//    }
//
//    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//        // 交换在数据源中相应数据源的位置
//        return this.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//    }
//
//    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//        TODO("Not yet implemented")
//    }
//}