package com.lengjiye.base.activity

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
        mViewModel = ViewModelProvider(this).get(getViewModel())
        mBinding.lifecycleOwner = this
        bindViewModel()
        mViewModel.onCreate()
        ActivityManager.singleton.add(this)
        initIntent(savedInstanceState)
        initView(savedInstanceState)
        initToolBar()
        initLiveDataListener()
        initData()
    }

    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): Class<VM>

    /**
     * 绑定 ViewModel
     */
    abstract fun bindViewModel()

    /**
     * 初始化 view
     * 设置view监听
     */
    open fun initView(savedInstanceState: Bundle?) = Unit

    /**
     * 初始化数据
     * 设置数据
     * 请求接口等
     */
    open fun initData() = Unit

    /**
     * intent 传值
     */
    open fun initIntent(savedInstanceState: Bundle?) = Unit

    /**
     * LiveData 数据监听
     */
    open fun initLiveDataListener() = Unit

    open fun initToolBar() = Unit

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.onDestroy()
        ActivityManager.singleton.remove(this)
    }

}