package com.lengjiye.tools.log

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lengjiye.code.baseparameter.constant.BaseEventConstant
import com.lengjiye.tools.R
import com.lengjiye.tools.ResTool


/**
 * 日志显示功能
 * 悬浮窗
 */
class LogService : LifecycleService() {

    private var mWindowManager: WindowManager? = null
    private var mParams: WindowManager.LayoutParams? = null
    private var textView: TextView? = null
    private var re: RelativeLayout? = null

    override fun onCreate() {
        super.onCreate()
        LogServiceInstance.isDestroy = false
        getWindowManager()
        LogServiceInstance.singleton.logContent.observe(this, Observer {
            textView?.append(ResTool.fromHtml(it).toString() + "\n")
        })

        LiveEventBus.get(BaseEventConstant.IS_BACK_GROUND, Boolean::class.java).observe(this, Observer {
            if (it) {
                re?.visibility = View.GONE
            } else {
                re?.visibility = View.VISIBLE
            }
        })

        addView()
    }

    private fun addView() {
        if (textView != null) {
            return
        }

        re = RelativeLayout(this)
        textView = TextView(this)
        val tvParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        textView?.layoutParams = tvParams
        textView?.setTextColor(ResTool.getColor(android.R.color.white))
        textView?.setBackgroundColor(ResTool.getColor(android.R.color.black))
        textView?.alpha = 0.6f
        textView?.textSize = 10f
        textView?.id = View.generateViewId()
        textView?.minLines = 5
        textView?.maxLines = 15
        textView?.movementMethod = ScrollingMovementMethod.getInstance()
        textView?.gravity = Gravity.BOTTOM
        re?.addView(textView)

        val button = Button(this)
        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        params.width = 60
        params.height = 70
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        button.setBackgroundResource(R.drawable.ic_delete_white_24dp)
        button.setOnClickListener {
            textView?.text = ""
        }
        re?.addView(button, params)
        addDragView()
        mWindowManager?.addView(re, mParams)
    }

    /**
     * 可以拖动的view
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun addDragView() {
        val dragView = ImageView(this)
        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        params.width = 70
        params.height = 70
        params.topMargin = -70
        textView?.id?.let { params.addRule(RelativeLayout.BELOW, it) }
        params.addRule(RelativeLayout.CENTER_HORIZONTAL)
        dragView.setImageResource(R.drawable.ic_drag_view_white_24dp)
        re?.addView(dragView, params)
        dragView.setOnTouchListener(object : View.OnTouchListener {
            // 触屏监听
            var lastX = 0f
            var lastY = 0f
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val action = event!!.action
                val x = event!!.x
                val y = event!!.y
                when (action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastX = x
                        lastY = y
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val mParamsx = mParams?.x?.plus((x - lastX).toInt() / 3)
                        val mParamsy = mParams?.y?.plus((y - lastY).toInt() / 3)
                        mParams?.x = mParamsx // 减小偏移量,防止过度抖动
                        mParams?.y = mParamsy // 减小偏移量,防止过度抖动
                        mWindowManager?.updateViewLayout(re, mParams)
                    }
                    MotionEvent.ACTION_UP -> {

                    }
                }
                return true
            }
        })
    }

    private fun getWindowManager() {
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager //获得WindowManager对象
        mParams = WindowManager.LayoutParams()
        mParams?.gravity = Gravity.TOP or Gravity.LEFT
        mParams?.x = 0
        mParams?.y = 100
        //总是出现在应用程序窗口之上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams?.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            mParams?.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        // 设置图片格式，效果为背景透明
        mParams?.format = PixelFormat.RGBA_8888

        mParams?.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                // 锁屏显示权限，可以不用
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON

        mParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        mParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        mWindowManager?.removeView(re)
        mWindowManager = null
        log("onDestroy")
        LogServiceInstance.isDestroy = true
    }
}