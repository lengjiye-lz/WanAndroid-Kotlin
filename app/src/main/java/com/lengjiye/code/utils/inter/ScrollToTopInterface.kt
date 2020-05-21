package com.lengjiye.code.utils.inter

import android.view.View

interface ScrollToTopInterface {
    /**
     * 平滑的返回指定位置
     *
     * @param view
     * @param position
     */
    fun goScrollToTopInterfaceAnimation(view: View, position: Int)

    /**
     * 瞬间返回指定位置
     *
     * @param view
     * @param position
     */
    fun goScrollToTopInterface(view: View, position: Int)
}