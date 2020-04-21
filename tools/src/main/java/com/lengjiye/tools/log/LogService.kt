package com.lengjiye.tools.log

import android.app.KeyguardManager
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.lengjiye.tools.ResTool


/**
 * 日志显示功能
 * 悬浮窗
 */
class LogService : LifecycleService() {

    private var mWindowManager: WindowManager? = null
    private var mParams: WindowManager.LayoutParams? = null
    private var textView: TextView? = null

    override fun onCreate() {
        super.onCreate()
        getWindowManager()
        LogServiceInstance.singleton.logContent.observe(this, Observer {
            textView?.append(ResTool.fromHtml(it).toString() + "\n")
        })
        LogServiceInstance.singleton.viewVisibility.observe(this, Observer {
            if (it) {
                textView?.visibility = View.VISIBLE
            } else {
                textView?.visibility = View.GONE
            }
        })

        add()
    }

    private fun add() {
        if (textView != null) {
            return
        }
        textView = TextView(this)
        textView?.setBackgroundColor(ResTool.getColor(android.R.color.black))
        textView?.setTextColor(ResTool.getColor(android.R.color.white))
        textView?.alpha = 0.6f
        textView?.textSize = 10f
        textView?.maxLines = 15
        textView?.movementMethod = ScrollingMovementMethod.getInstance()
        textView?.gravity = Gravity.BOTTOM

        mWindowManager?.addView(textView, mParams)
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
        mWindowManager?.removeView(textView)
        mWindowManager = null
    }
}