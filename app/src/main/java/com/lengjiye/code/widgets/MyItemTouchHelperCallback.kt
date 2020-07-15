package com.lengjiye.code.widgets

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.lengjiye.base.recycleview.BaseDBAdapter
import com.lengjiye.tools.log.logE


/**
 * 处理滑动删除
 */
class MyItemTouchHelperCallback(val adapter: BaseDBAdapter<*, *, *>) : ItemTouchHelper.Callback() {

    /**
     * 保存上次操作的view
     */
    private var viewHolder: RecyclerView.ViewHolder? = null

    constructor(maxScrollX: Int, springBackScrollX: Int, adapter: BaseDBAdapter<*, *, *>) : this(adapter) {
        this.mDefaultScrollX = maxScrollX
        this.springBackScrollX = springBackScrollX
    }

    /**
     * 开始拖动的位置
     */
    private var moveStartScrollX = 0

    /**
     * 动画开始位置
     */
    private var animationStartScrollX = 0f

    /**
     * 滑动最大位置
     */
    private var mDefaultScrollX = 600

    /**
     * 回弹位置
     */
    private var springBackScrollX = 150

    /**
     * 滑动方向
     */
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
//        if (viewHolder.itemView is ViewGroup){
//            (viewHolder.itemView as ViewGroup).addView(Button(recyclerView.context))
//            LogTool.de("add Button")
//        }


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
        if (viewHolder.itemView.scrollX > mDefaultScrollX)
            viewHolder.itemView.scrollTo(mDefaultScrollX, 0)
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
        this.viewHolder?.let {
            if (viewHolder != null && it != viewHolder) {
                resetViewLocation(it.itemView)
            }
        }
        logE("MyItemTouchHelperCallback", "viewHolder:$viewHolder")
    }

    /**
     * 针对swipe和drag状态，当手指离开之后，view回到指定位置动画的持续时间(swipe可能是回到原位，也有可能是swipe掉)
     */
    override fun getAnimationDuration(recyclerView: RecyclerView, animationType: Int, animateDx: Float, animateDy: Float): Long {
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy)
    }

    /**
     * 针对swipe和drag状态，整个过程中一直会调用这个函数,随手指移动的view就是在super里面做到的(和ItemDecoration里面的onDraw()函数对应)
     */
    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (dX == 0f) {
            moveStartScrollX = viewHolder.itemView.scrollX
        }
        val scroll = viewHolder.itemView.scrollX
        var sc = (moveStartScrollX - dX).toInt()
//        LogTool.e("MyItemTouchHelperCallback", "dX:$dX scroll:$scroll sc:$sc animationStartScrollX:$animationStartScrollX isCurrentlyActive:$isCurrentlyActive")
        if (isCurrentlyActive) {
            // 手指滑动
            if (sc <= 0)
                sc = 0
            else if (sc >= mDefaultScrollX)
                sc = mDefaultScrollX
            viewHolder.itemView.scrollTo(sc, 0)
            animationStartScrollX = dX
        } else {
            if (dX < 0) {
                // 向左滑动
                if (sc > mDefaultScrollX) {
                    return
                }
                // 获得动画执行的百分比
                val animatedFraction = (dX / animationStartScrollX * 100f).toInt() / 100f
                if (scroll < springBackScrollX) {
                    viewHolder.itemView.scrollTo(sc.coerceAtLeast(0), 0)
                } else {
                    this.viewHolder = viewHolder
                    val scr = (-animationStartScrollX + (mDefaultScrollX + animationStartScrollX) * (1f - animatedFraction)).toInt()
                    viewHolder.itemView.scrollTo(scr.coerceAtMost(mDefaultScrollX), 0)
                }
            } else if (dX > 0) {
                // 右滑动
                if (scroll <= 0) {
                    return
                }
                if (scroll >= mDefaultScrollX - springBackScrollX) {
                    viewHolder.itemView.scrollTo(mDefaultScrollX - dX.toInt(), 0)
                } else {
                    val scr = dX.toInt().coerceAtMost(scroll - 20)
                    viewHolder.itemView.scrollTo(scr.coerceAtLeast(0), 0)
                }
            }
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
        return Integer.MAX_VALUE.toFloat()
    }

    /**
     * 针对swipe状态，swipe滑动的位置超过了百分之多少就消失
     */
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 2f
    }

    /**
     * 针对swipe状态，swipe滑动的阻尼系数,设置最大滑动速度
     */
    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return super.getSwipeVelocityThreshold(defaultValue)
    }

    /**
     * 重置view坐标
     */
    fun resetViewLocation(view: View? = viewHolder?.itemView) {
        view?.scrollTo(0, 0)
    }
}