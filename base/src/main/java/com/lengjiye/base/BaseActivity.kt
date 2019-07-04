package com.lengjiye.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.lengjiye.base.viewmode.BaseViewMode

abstract class BaseActivity<out T : ViewDataBinding, out M : BaseViewMode> : AppCompatActivity() {

    private lateinit var mBinding: T
    private lateinit var mViewModel: M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = ViewModelProviders.of(this).get(getViewModel()::class.java)
        mBinding.setLifecycleOwner(this)
//        mBinding.viewModel = mViewModel
    }

    abstract fun getLayoutId(): Int

    abstract fun getViewModel() : M

    abstract fun getViewDataBinding(): T


}