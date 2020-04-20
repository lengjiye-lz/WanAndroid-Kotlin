package com.lengjiye.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lengjiye.base.activity.BaseActivity
import com.lengjiye.base.viewmodel.BaseViewModel

/**
 * 基类
 */
abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    lateinit var mBinding: T
    lateinit var mViewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mViewModel = ViewModelProvider(getBaseActivity()).get(getViewModel())
        mBinding.lifecycleOwner = this
        bindViewModel()
        mViewModel.onCreate()
        initView(savedInstanceState)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
     *
     * view监听事件 等
     */
    open fun initView(savedInstanceState: Bundle?) = Unit

    /**
     * 初始化数据
     * 设置数据
     * 请求接口等
     */
    open fun initData() = Unit

    /**
     * LiveData 数据监听
     */
    open fun initLiveDataListener() = Unit

    fun getBaseActivity(): BaseActivity<*, *> {
        return activity as BaseActivity<*, *>
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.onDestroy()
    }
}