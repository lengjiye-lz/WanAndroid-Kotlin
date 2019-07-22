package com.lengjiye.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.lengjiye.base.viewmode.BaseViewMode

abstract class BaseActivity<T : ViewDataBinding, VM : BaseViewMode> : AppCompatActivity() {

    lateinit var mBinding: T
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = ViewModelProviders.of(this).get(getViewModel()::class.java)
        mBinding.lifecycleOwner = this

        bindViewModel()

        initView(savedInstanceState)
    }

    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): VM

    /**
     * 绑定 ViewModel
     */
    abstract fun bindViewModel()

    /**
     * 初始化 view
     */
    open fun initView(savedInstanceState: Bundle?) = Unit

    open fun initData() = Unit
}