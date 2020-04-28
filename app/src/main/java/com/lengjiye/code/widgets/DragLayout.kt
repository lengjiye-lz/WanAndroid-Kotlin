package com.lengjiye.code.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.customview.widget.ViewDragHelper
import com.lengjiye.code.R
import com.lengjiye.tools.log.LogTool

/**
 * 可以拖动的悬浮球
 */
class DragLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var mDrag: ViewDragHelper
    private var lastX = -1
    private var lastY = -1
    private lateinit var iv: ImageView

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {

//        LayoutInflater.from(context).inflate()

        mDrag = ViewDragHelper.create(this, 0.1f, object : ViewDragHelper.Callback() {

            private var mLeft: Int = 0
            private var mTop: Int = 0

            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                LogTool.e("lz", "33333:${iv.id == child.id}")
                return iv.id == child.id
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
                LogTool.e("lz", "releasedChild:${releasedChild.id}")
                LogTool.e("lz", "xvel:${xvel}")
                LogTool.e("lz", "yvel:${yvel}")
                mDrag.settleCapturedViewAt(mLeft, mTop)
                invalidate()
            }

            override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)
                lastX = changedView.x.toInt()
                lastY = changedView.y.toInt()
            }
        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        iv = this.findViewById(R.id.iv_drag)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        restorePosition()
    }

    private fun restorePosition() {
        if (lastX == -1 && lastY == -1) {
            lastX = measuredWidth - iv.measuredWidth
            lastY = 0
        }
        iv.layout(lastX, lastY, lastX + iv.measuredWidth, lastY + iv.measuredHeight)
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev != null) {
            return mDrag.shouldInterceptTouchEvent(ev)
        }
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            mDrag.processTouchEvent(event)

        }
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mDrag.continueSettling(true)) {
            invalidate()
        }
    }

}