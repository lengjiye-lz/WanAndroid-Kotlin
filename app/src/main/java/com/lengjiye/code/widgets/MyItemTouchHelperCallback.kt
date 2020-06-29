package com.lengjiye.code.widgets

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.lengjiye.base.recycleview.BaseDBAdapter
import com.lengjiye.tools.log.LogTool

/**
 * 处理滑动删除
 */
class MyItemTouchHelperCallback(val adapter: BaseDBAdapter<*, *, *>) : ItemTouchHelper.Callback() {

    private var mCurrentScrollX = 0
    private var mCurrentScrollXWhenInactive = 0
    private var mInitXWhenInactive = 0f
    private val mDefaultScrollX = 400f
    private var mFirstInactive = false
    private var currentScrollX = 0

    /**
     * 滑动方向
     */
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        // 向左滑动
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // 不处理拖动事件
        return false
    }

    /**
     * 针对swipe状态，swipe 到达滑动消失的距离回调函数,一般在这个函数里面处理删除item的逻辑
     * 确切的来讲是swipe item滑出屏幕动画结束的时候调用
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        LogTool.de("onSwiped")
        val position = viewHolder.adapterPosition
        adapter.getItems().removeAt(position)
        adapter.notifyItemRemoved(position)
        adapter.notifyDataSetChanged()
    }

    /**
     * 针对swipe和drag状态，当一个item view在swipe、drag状态结束的时候调用
     * drag状态：当手指释放的时候会调用
     * swipe状态：滑动结束回弹时调用，删除后刷新view调用，一般我们会在onSwiped()函数里面删除掉指定的item view
     */
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        LogTool.de("clearView")

        if (viewHolder.itemView.scrollX > mDefaultScrollX)
            viewHolder.itemView.scrollTo(mDefaultScrollX.toInt(), 0)
        else if (viewHolder.itemView.scrollX < 0)
            viewHolder.itemView.scrollTo(0, 0)

    }

    /**
     * 针对swipe状态，是否允许swipe(滑动)操作
     */
    override fun isItemViewSwipeEnabled(): Boolean {
        return super.isItemViewSwipeEnabled()
    }

    /**
     * 针对swipe和drag状态，当swipe或者drag对应的ViewHolder改变的时候调用
     * 我们可以通过重写这个函数获取到swipe、drag开始和结束时机，viewHolder 不为空的时候是开始，空的时候是结束
     */
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        LogTool.de("onSelectedChanged viewHolder:$viewHolder")
    }

    /**
     * 针对swipe和drag状态，当手指离开之后，view回到指定位置动画的持续时间(swipe可能是回到原位，也有可能是swipe掉)
     */
    override fun getAnimationDuration(recyclerView: RecyclerView, animationType: Int, animateDx: Float, animateDy: Float): Long {
        LogTool.de("getAnimationDuration animationType:$animationType  animateDx:$animateDx  animateDy:$animateDy")
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy)
    }

    /**
     * 针对swipe和drag状态，整个过程中一直会调用这个函数,随手指移动的view就是在super里面做到的(和ItemDecoration里面的onDraw()函数对应)
     */
    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        if (dX == 0f) {
            mCurrentScrollX = viewHolder.itemView.scrollX
            mFirstInactive = true
        }
        val scroll = viewHolder.itemView.scrollX

//        LogTool.de("onChildDraw dX:$dX  dY:$dY  isCurrentlyActive:$isCurrentlyActive  currentScrollX:$currentScrollX  scroll:$scroll")

        currentScrollX = scroll

        var sc = (mCurrentScrollX - dX).toInt()
        LogTool.de("dX:$dX  (mCurrentScrollX - dX).toInt():${sc} mDefaultScrollX:$mDefaultScrollX")
        if (isCurrentlyActive) {
            // 手指滑动
            if (sc <= 0)
                sc = 0
            else if (sc >= mDefaultScrollX.toInt())
                sc = mDefaultScrollX.toInt()
            viewHolder.itemView.scrollTo(sc, 0);
        } else {
            if (dX < 0) {
                // 向左滑动
                if (scroll < 100) {
                    viewHolder.itemView.scrollTo(sc.coerceAtLeast(0), 0)
                } else {
                    viewHolder.itemView.scrollTo(sc.coerceAtMost(mDefaultScrollX.toInt()), 0)
                }
            } else {
                // 右滑动
                if (scroll >= mDefaultScrollX - 100) {
                    viewHolder.itemView.scrollTo(sc.coerceAtLeast(mDefaultScrollX.toInt()), 0)
                } else {
                    viewHolder.itemView.scrollTo(sc.coerceAtMost(0), 0)
                }
            }
//            // 动画滑动
//            if (mFirstInactive) {
//                mFirstInactive = false
//                mCurrentScrollXWhenInactive = viewHolder.itemView.scrollX
//                mInitXWhenInactive = dX
//            }
//            if ((sc >= 100 && dX <= 0) || (sc >= mDefaultScrollX - 100 && dX >= 0)) {
//                // 当手指松开时，ItemView的滑动距离大于给定阈值，那么最终就停留在阈值，显示删除按钮。
//                viewHolder.itemView.scrollTo(sc.coerceAtLeast(mDefaultScrollX.toInt()), 0)
//            } else if ((sc < 100 && dX <= 0) || (sc < mDefaultScrollX - 100 && dX >= 0)) {
//                // 这里只能做距离的比例缩放，因为回到最初位置必须得从当前位置开始，dx不一定与ItemView的滑动距离相等
//                viewHolder.itemView.scrollTo(sc.coerceAtMost(0), 0)
//            }
        }


    }

    /**
     * 针对swipe和drag状态，整个过程中一直会调用这个函数(和ItemDecoration里面的onDrawOver()函数对应)
     * 这个函数提供给我们可以在RecyclerView的上面再绘制一层东西，比如绘制一层蒙层啥的
     */
    override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    /**
     * 针对swipe状态，swipe的逃逸速度，换句话说就算没达到getSwipeThreshold设置的距离，达到了这个逃逸速度item也会被swipe消失掉
     */
    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        LogTool.de("getSwipeEscapeVelocity defaultValue:$defaultValue")
        return Integer.MAX_VALUE.toFloat()
    }

    /**
     * 针对swipe状态，swipe滑动的位置超过了百分之多少就消失
     */
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        LogTool.de("getSwipeThreshold:${super.getSwipeThreshold(viewHolder)})")
        return 2f
    }

    /**
     * 针对swipe状态，swipe滑动的阻尼系数,设置最大滑动速度
     */
    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        LogTool.de("getSwipeVelocityThreshold:${super.getSwipeVelocityThreshold(defaultValue)})")
        return super.getSwipeVelocityThreshold(defaultValue)
    }
}