package com.lengjiye.code.utils

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lengjiye.code.widgets.SpaceDecoration

object LayoutManagerUtils {

    fun horizontalLinearLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    fun verticalLinearLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    fun gridLayoutManager(context: Context, spanCount: Int): GridLayoutManager {
        return GridLayoutManager(context, spanCount)
    }

    fun gridLayoutManager(
        context: Context, spanCount: Int, orientation: Int,
        reverseLayout: Boolean
    ): GridLayoutManager {
        return GridLayoutManager(context, spanCount, orientation, reverseLayout)
    }

    fun staggeredManager(spanCount: Int, orientation: Int): StaggeredGridLayoutManager {
        return StaggeredGridLayoutManager(spanCount, orientation)
    }

    /**
     * 四周添加间隔
     */
    fun borderDivider(spaceLeft: Int, spaceTop: Int, spaceRight: Int, spaceBottom: Int): RecyclerView.ItemDecoration {
        return SpaceDecoration(spaceLeft, spaceTop, spaceRight, spaceBottom)
    }
}