package com.lengjiye.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.lengjiye.base.viewmodel.BaseViewModel

abstract class BaseActivity<T : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    lateinit var mBinding: T
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = ViewModelProvider(this).get(getViewModel()::class.java)
        mBinding.lifecycleOwner = this
        bindViewModel()
        mViewModel.onCreate()
        initView(savedInstanceState)
        initToolBar()
        initData()
        initLiveDataListener()
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

    /**
     * LiveData 数据监听
     */
    open fun initLiveDataListener() = Unit

    open fun initToolBar() = Unit

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.onDestroy()
    }
}