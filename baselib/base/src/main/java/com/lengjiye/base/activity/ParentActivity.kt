package com.lengjiye.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.lengjiye.base.utils.ClassUtil
import com.lengjiye.base.viewmodel.BaseViewModel

/**
 * 基类，不掺杂业务，没有必要不建议修改
 */
abstract class ParentActivity<T : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var mBinding: T
    lateinit var mViewModel: VM
    lateinit var baseBinding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val baseLayout = getBaseLayoutId()
        if (baseLayout == null) {
            mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        } else {
            baseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), baseLayout, null, true)
            mBinding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), null, true)
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            if (baseBinding.root is ViewGroup) {
                (baseBinding.root as ViewGroup).addView(mBinding.root, params)
            }
            initBaseView(savedInstanceState)
            window.setContentView(baseBinding.root)
        }
        mBinding.lifecycleOwner = this
        initIntent(savedInstanceState)
        initView(savedInstanceState)
        initToolBar()
        initViewModel()
        initLiveDataListener()
        initData()
    }

    abstract fun getLayoutId(): Int

    /**
     * 在原有的布局外面添加一些公共的控件，比如悬浮窗，异常界面等
     * 如果不需要显示则重写此方法，返回null
     *
     * 可以使用<merge>标签优化
     */
    protected open fun getBaseLayoutId(): Int? {
        return null
    }

    /**
     * 初始化 BaseView
     * 设置view监听
     * 只有在含有baseLayout的时候起作用
     */
    open fun initBaseView(savedInstanceState: Bundle?) = Unit

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
        val instance = AndroidViewModelFactory
            .getInstance(application)
        val viewModelClass = ClassUtil.getViewModel<VM>(this) ?: return
        mViewModel = ViewModelProvider(this, instance).get(viewModelClass)
    }
}