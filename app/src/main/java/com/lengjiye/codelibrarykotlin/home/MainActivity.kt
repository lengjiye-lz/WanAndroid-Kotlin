package com.lengjiye.codelibrarykotlin.home

import android.os.Bundle
import com.lengjiye.base.BaseActivity
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.databinding.ActivityMainBinding
import com.lengjiye.codelibrarykotlin.home.viewmodel.HomeViewMode

/**
 * mainActivity
 */
class MainActivity : BaseActivity<ActivityMainBinding, HomeViewMode>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): HomeViewMode {
        return HomeViewMode(application)
    }

    override fun bindViewModel() {
        getBinding()?.viewModel = mViewModel
    }

    /**
     * 获取 mBinding
     */
    private fun getBinding(): ActivityMainBinding? {
        return mBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    override fun initData() {
        super.initData()
    }
}
