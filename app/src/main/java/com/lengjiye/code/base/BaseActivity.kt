package com.lengjiye.code.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.lengjiye.base.activity.ParentActivity
import com.lengjiye.base.viewmodel.BaseViewModel
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityBaseBinding
import com.lengjiye.code.utils.toast
import com.lengjiye.code.widgets.FloatingHolder

/**
 * 基类
 * 可以添加和修改一些公共部分，比如添加全局悬浮窗
 */
abstract class BaseActivity<T : ViewDataBinding, VM : BaseViewModel> : ParentActivity<T, VM>() {

    override fun getBaseLayoutId(): Int? {
        return R.layout.activity_base
    }

    override fun initBaseView(savedInstanceState: Bundle?) {
        super.initBaseView(savedInstanceState)
        val floatingView = LayoutInflater.from(this).inflate(R.layout.base_suspension_layout, null, false)
        val floatingParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        (baseBinding as ActivityBaseBinding).dlLayout.addView(floatingView, floatingParams)
        FloatingHolder.singleton.addObserver((baseBinding as ActivityBaseBinding).dlLayout)
        (baseBinding as ActivityBaseBinding).dlLayout.setOnClickListener{
            "我是悬浮球".toast()
        }
    }
}
