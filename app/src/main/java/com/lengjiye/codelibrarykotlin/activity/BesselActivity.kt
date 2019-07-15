package com.lengjiye.codelibrarykotlin.activity

import androidx.databinding.ViewDataBinding
import com.lengjiye.base.BaseActivity
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.databinding.ActivityBesselBinding
import com.lengjiye.codelibrarykotlin.viewmode.BesselViewMode

/**
 * 贝塞尔曲线测试 demo
 */
open class BesselActivity : BaseActivity<ViewDataBinding, BesselViewMode>() {

    private var i: Int = 0

    override fun getViewModel(): BesselViewMode {
        return BesselViewMode(application)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_bessel
    }

    override fun bindViewModel() {
        getBinding()?.viewModel = mViewModel
    }

    /**
     * 获取 mBinding
     */
    private fun getBinding(): ActivityBesselBinding? {
        return mBinding as ActivityBesselBinding
    }

    override fun initView() {
        getBinding()?.tvText?.text = "测试  测试  测试"
        getBinding()?.tvText?.setOnClickListener {
            i++
            getBinding()?.tvText?.text = "测试  测试  测试:$i"
        }
    }

}



