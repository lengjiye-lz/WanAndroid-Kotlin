package com.lengjiye.code.base

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lengjiye.base.activity.ParentActivity
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityBaseBinding
import com.lengjiye.code.todo.activity.TodoActivity
import com.lengjiye.code.utils.inter.ScrollToTopInterface
import com.lengjiye.code.utils.startActivity
import com.lengjiye.code.widgets.FloatingHolder

/**
 * 基类
 * 可以添加和修改一些公共部分，比如添加全局悬浮窗
 */
abstract class BaseActivity<T : ViewDataBinding, VM : BaseViewModel> : ParentActivity<T, VM>(), ScrollToTopInterface {

    override fun getBaseLayoutId(): Int? {
        return R.layout.activity_base
    }

    override fun initBaseView(savedInstanceState: Bundle?) {
        super.initBaseView(savedInstanceState)
        val floatingView = LayoutInflater.from(this).inflate(R.layout.base_suspension_layout, null, false)
        val floatingParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        (baseBinding as ActivityBaseBinding).flLayout.addView(floatingView, floatingParams)
        FloatingHolder.singleton.addObserver((baseBinding as ActivityBaseBinding).flLayout)
        (baseBinding as ActivityBaseBinding).flLayout.setOnClickListener {
            startActivity<TodoActivity>()
        }
    }

    override fun goScrollToTopInterfaceAnimation(view: View, position: Int) {
        if (view is RecyclerView) {
            view.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0f, 0f, 0))
            // 可见的第一个view
            val firstItem: Int = view.getChildLayoutPosition(view.getChildAt(0))
            // 可见的最后一个view
            val lastItem: Int = view.getChildLayoutPosition(view.getChildAt(view.childCount - 1))
            if (position < firstItem) {
                // 超过20的由于滑动时间很长，所以直接跳转到到20到位置开始执行动画，减少滑动时间
                if (firstItem > 20) {
                    (view.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(20, 0)
                }
                // 如果要跳转的位置在第一个可见项之前，则smoothScrollToPosition可以直接跳转
                view.smoothScrollToPosition(position)
            } else if (position <= lastItem) {
                // 如果要跳转的位置在第一个可见项之后，且在最后一个可见项之前
                // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
                val movePosition = position - firstItem
                if (movePosition >= 0 && movePosition < view.childCount) {
                    (view.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
                }
            }
        }
    }

    override fun goScrollToTopInterface(view: View, position: Int) {
        if (view is RecyclerView) {
            view.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0f, 0f, 0))
            (view.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
        }
    }
}
