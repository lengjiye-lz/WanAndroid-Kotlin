package com.lengjiye.base.fragment

import androidx.databinding.ViewDataBinding
import com.lengjiye.base.viewmodel.BaseViewModel


/**
 * 懒加载基类
 * 新的方式进行懒加载
 *
 * 适用于viewpager
 */
abstract class NewLazyParentFragment<T : ViewDataBinding, VM : BaseViewModel> : ParentFragment<T, VM>() {
    private var isFirst = true

    /**
     * 请求加在数据
     *
     * 加载第一次请求数据
     */
    abstract fun lazyData()

    override fun onResume() {
        super.onResume()
        if (isFirst) {
            lazyData()
        }
        isFirst = false
    }
}