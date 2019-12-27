package com.lengjiye.code.widgets

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * recycleview item 间隔
 */
class SpaceDecoration : RecyclerView.ItemDecoration {

    private var spaceLeft: Int = 0
    private var spaceTop: Int = 0
    private var spaceRight: Int = 0
    private var spaceBottom: Int = 0

    constructor(space: Int) {
        this.spaceLeft = space
        this.spaceTop = space
        this.spaceRight = space
        this.spaceBottom = space
    }

    constructor(spaceLeft: Int, spaceTop: Int, spaceRight: Int, spaceBottom: Int) {
        this.spaceLeft = spaceLeft
        this.spaceTop = spaceTop
        this.spaceRight = spaceRight
        this.spaceBottom = spaceBottom
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        when (parent.layoutManager) {
            is GridLayoutManager -> {
                gridLayout(outRect, view, parent)
            }
            is LinearLayoutManager -> {
                linearLayout(outRect, view, parent)
            }
            else -> {

            }
        }
    }

    /**
     * 设置linearLayout边界
     */
    private fun linearLayout(outRect: Rect, view: View, parent: RecyclerView) {
        val count = parent.adapter?.itemCount
        val manager = parent.layoutManager as LinearLayoutManager
        val position = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        // 垂直列表
        if (manager.orientation == LinearLayoutManager.VERTICAL) {
            if (position == 0) {
                // 第一个
                outRect.set(spaceLeft, spaceTop, spaceRight, 0)
            } else if (count != null && position == count - 1) {
                // 最后一个
                outRect.set(spaceLeft, 0, spaceRight, spaceBottom)
            } else {
                outRect.set(spaceLeft, 0, spaceRight, 0)
            }
        } else {
            // 横向列表
            if (position == 0) {
                // 第一个
                outRect.set(spaceLeft, spaceTop, 0, spaceBottom)
            } else if (count != null && position == count - 1) {
                // 最后一个
                outRect.set(0, spaceTop, spaceRight, spaceBottom)
            } else {
                outRect.set(0, spaceTop, 0, spaceBottom)
            }
        }
    }

    /**
     * 设置linearLayout边界
     */
    private fun gridLayout(outRect: Rect, view: View, parent: RecyclerView) {
        val count = parent.adapter?.itemCount
        val manager = parent.layoutManager as GridLayoutManager
        var spanCount = manager.spanCount

        if (manager.orientation == GridLayoutManager.HORIZONTAL && count != null) {
            spanCount = count / spanCount
        }

        val position = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        var lastRow = 0
        if (count != null) {
            // 计算最后一排第一个的坐标
            lastRow = if (count % spanCount == 0) {
                count - spanCount
            } else {
                (count / spanCount) * spanCount
            }
        }

        // 第一排
        when {
            position < spanCount -> {
                when {
                    position == 0 -> {
                        // 第一行第一个
                        outRect.set(spaceLeft, spaceTop, 0, 0)
                    }
                    position + 1 == spanCount -> {
                        // 第一行最后一个
                        outRect.set(0, spaceTop, spaceRight, 0)
                    }
                    else -> {
                        outRect.set(0, spaceTop, 0, 0)
                    }
                }
            }
            position >= lastRow -> {
                // 最后一排
                when (position) {
                    lastRow -> {
                        // 最后一排第一个
                        outRect.set(spaceLeft, 0, 0, spaceBottom)
                    }
                    lastRow + spanCount - 1 -> {
                        // 最后一排最后一个
                        outRect.set(0, 0, spaceRight, spaceBottom)
                    }
                    else -> {
                        outRect.set(0, 0, 0, spaceBottom)
                    }
                }

            }
            position % spanCount == 0 -> {
                // 左边一列
                outRect.set(spaceLeft, 0, 0, 0)
            }
            (position + 1) % spanCount == 0 -> {
                // 右边一列
                outRect.set(0, 0, spaceRight, 0)
            }

        }
    }
}