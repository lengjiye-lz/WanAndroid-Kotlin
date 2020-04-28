package com.lengjiye.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.lengjiye.base.utils.ClassUtil
import com.lengjiye.base.viewmodel.BaseViewModel

/**
 * 基类，不掺杂业务，没有必要不建议修改
 */
abstract class ParentActivity<T : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    lateinit var mBinding: T
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.lifecycleOwner = this
        ActivityManager.singleton.add(this)
        initIntent(savedInstanceState)
        initView(savedInstanceState)
        initToolBar()
        initViewModel()
        initLiveDataListener()
        initData()
    }

    abstract fun getLayoutId(): Int

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

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        val viewModelClass = ClassUtil.getViewModel<VM>(this) ?: return
        mViewModel = ViewModelProvider(this).get(viewModelClass)
        mViewModel.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.singleton.remove(this)
    }


}