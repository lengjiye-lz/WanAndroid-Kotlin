package com.lengjiye.tools.log

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer

/**
 * 日志显示功能
 */
class LogService : LifecycleService() {

    private var mWindowManager: WindowManager? = null
    private var mParams: WindowManager.LayoutParams? = null
    private var textView: TextView? = null

    override fun onCreate() {
        super.onCreate()
        getWindowManager()
        LogServiceInstance.singleton.logMessage.observe(this, Observer {
            textView?.let { view ->
                view.text = it
            }
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        show()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun show() {
        if (textView != null) {
            return
        }
        textView = TextView(this)
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
        //设置图片格式，效果为背景透明
        mParams?.format = PixelFormat.RGBA_8888
        mParams?.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        mParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        mParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        mWindowManager?.removeView(textView)
        mWindowManager = null
    }
}