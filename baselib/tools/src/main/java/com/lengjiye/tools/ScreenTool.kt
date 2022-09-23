package com.lengjiye.tools

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.lengjiye.code.baseparameter.application.MasterApplication


object ScreenTool {

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    fun getScreenWidth(context: Context = MasterApplication.getInstance().applicationContext()): Int {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    fun getScreenHeight(context: Context = MasterApplication.getInstance().applicationContext()): Int {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    /**
     * 获得状态栏的高度
     *
     * @return
     */
    fun getStatusHeight(): Int {
        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen.xml")
            val `object` = clazz.newInstance()
            val height = clazz.getField("status_bar_height")[`object`].toString().toInt()
            statusHeight = ResTool.getDimens(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusHeight
    }
}