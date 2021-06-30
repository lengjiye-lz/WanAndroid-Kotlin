package com.lengjiye.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lengjiye.base.utils.ClassUtil
import com.lengjiye.base.activity.ParentActivity
import com.lengjiye.base.viewmodel.BaseViewModel

/**
 * 基类，不掺杂业务，如没有必要，不建议修改
 */
abstract class ParentFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    lateinit var mBinding: T
    lateinit var mViewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mBinding.lifecycleOwner = this
        initView(savedInstanceState)
        initViewModel()
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLiveDataListener()
        initData()
    }

    abstract fun getLayoutId(): Int

    /**
     * 初始化 view
     *
     * view监听事件 等
     *
     * 注：ViewModel还没有初始化
     */
    open fun initView(savedInstanceState: Bundle?) = Unit

    /**
     * 初始化ViewModel
     */
    private fun initViewModel(){
        val viewModelClass = ClassUtil.getViewModel<VM>(this) ?: return
        mViewModel = ViewModelProvider(this).get(viewModelClass)
//        mViewModel.onCreate()
    }

    /**
     * 初始化数据
     * 设置数据
     * 请求接口等
     * 懒加载不不建议使用此方法
     */
    protected open fun initData() = Unit

    /**
     * LiveData 数据监听
     * 懒加载不建议使用此方法
     */
    open fun initLiveDataListener() = Unit

    fun getBaseActivity(): ParentActivity<*, *> {
        return activity as ParentActivity<*, *>
    }
}