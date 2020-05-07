package com.lengjiye.code.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.lengjiye.code.R
import com.lengjiye.code.utils.toast
import com.lengjiye.tools.SPTool
import java.util.*

/**
 * 可以拖动的悬浮球
 */
class FloatingLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr), Observer {

    private lateinit var mDrag: ViewDragHelper
    private lateinit var iv: View

    private companion object {
        const val KEY_FLOATING_X = "KEY_FLOATING_X"
        const val KEY_FLOATING_Y = "KEY_FLOATING_Y"
    }

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        mDrag = ViewDragHelper.create(this, 1f, object : ViewDragHelper.Callback() {

            private var mLeft: Int = 0
            private var mTop: Int = 0

            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return iv == child
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                mTop = when {
                    top > height - child.measuredHeight -> {
                        height - child.measuredHeight
                    }
                    top < 0 -> {
                        0
                    }
                    else -> {
                        top
                    }
                }
                return mTop
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                mLeft = when {
                    left > width - child.measuredWidth -> {
                        width - child.measuredWidth
                    }
                    left < 0 -> {
                        0
                    }
                    else -> {
                        left
                    }
                }
                return mLeft
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                super.onViewReleased(releasedChild, xvel, yvel)
                if (releasedChild != iv) {
                    return
                }
                var x = releasedChild.x
                var y = releasedChild.y
                if (x < (measuredWidth / 2f - releasedChild.measuredWidth / 2f)) {
                    when {
                        x < releasedChild.measuredWidth / 3f -> {
                            x = 0f
                        }
                        y < releasedChild.measuredHeight * 3 -> { // 0-y/3
                            y = 0f
                        }
                        y > measuredHeight - releasedChild.measuredHeight * 3 -> { // 0-(y-y/3)
                            y = measuredHeight - releasedChild.measuredHeight.toFloat()
                        }
                        else -> {
                            x = 0f
                        }
                    }
                } else {
                    when {
                        x > measuredWidth - releasedChild.measuredWidth / 3f - releasedChild.measuredWidth -> {
                            x = measuredWidth - releasedChild.measuredWidth.toFloat()
                        }
                        y < releasedChild.measuredHeight * 3 -> { // 0-y/3
                            y = 0f
                        }
                        y > measuredHeight - releasedChild.measuredHeight * 3 -> { // 0-(y-y/3)
                            y = measuredHeight - releasedChild.measuredHeight.toFloat()
                        }
                        else -> {
                            x = measuredWidth - releasedChild.measuredWidth.toFloat()
                        }
                    }
                }
                mDrag.settleCapturedViewAt(x.toInt(), y.toInt())
                invalidate()
            }

            override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)
                savePosition()
            }

            override fun getViewHorizontalDragRange(child: View): Int {
                return measuredWidth - child.measuredWidth
            }

            override fun getViewVerticalDragRange(child: View): Int {
                return measuredHeight - child.measuredHeight
            }

            override fun onViewDragStateChanged(state: Int) {
                super.onViewDragStateChanged(state)
                if (state == ViewDragHelper.STATE_SETTLING) { // 拖拽结束，通知观察者
                    FloatingHolder.singleton.update()
                }
            }

        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // 获取可以被拖动的控件
        iv = this.findViewById(R.id.iv_logo)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        restorePosition()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        savePosition()
        FloatingHolder.singleton.deleteObserver(this)
    }

    /**
     * 保存位置
     */
    private fun savePosition() {
        val x = iv.x
        val y = iv.y
        SPTool.putFloat(KEY_FLOATING_X, x)
        SPTool.putFloat(KEY_FLOATING_Y, y)
    }

    /**
     * 更新位置
     */
    private fun restorePosition() {
        var x = SPTool.getFloat(KEY_FLOATING_X, -1f)
        var y = SPTool.getFloat(KEY_FLOATING_Y, -1f)

        if (x == -1f && y == -1f) {
            x = (measuredWidth - iv.measuredWidth).toFloat()
            y = (measuredHeight * 3 / 4).toFloat()
        }
        iv.layout(x.toInt(), y.toInt(), (x + iv.measuredWidth).toInt(), (y + iv.measuredHeight).toInt())
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var b = false
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val x = ev.x
            val y = ev.y

            val view = findChildUnder(x.toInt(), y.toInt())
            b = view == iv
        }

        if (ev.action == MotionEvent.ACTION_CANCEL || ev.action == MotionEvent.ACTION_UP) {
            mDrag.cancel()
            return false
        }

        return if (b) {
            mDrag.processTouchEvent(ev)
            true
        } else {
            mDrag.shouldInterceptTouchEvent(ev)
        }
    }


    private fun findChildUnder(x: Int, y: Int): View? {
        if (x >= iv.left && x < iv.right && y >= iv.top && y < iv.bottom) {
            return iv
        }
        return null
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDrag.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (mDrag.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun update(o: Observable?, arg: Any?) {
        restorePosition()
    }
}