package com.lengjiye.codelibrarykotlin.activity

import android.os.Bundle
import com.lengjiye.base.BaseActivity
import com.lengjiye.base.application.MasterApplication
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.databinding.ActivityBesselBinding
import com.lengjiye.codelibrarykotlin.model.BesselModel
import com.lengjiye.codelibrarykotlin.viewmode.BesselViewMode

/**
 * 贝塞尔曲线测试 demo
 */
open class BesselActivity : BaseActivity<ActivityBesselBinding, BesselViewMode<BesselModel>>() {

    private var i: Int = 0

    override fun getViewModel(): BesselViewMode<BesselModel> {
        return BesselViewMode(application, BesselModel())
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_bessel
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    /**
     * 获取 mBinding
     */
    private fun getBinding(): ActivityBesselBinding {
        return mBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
        getBinding().tvText.text = "测试  测试  测试"
        getBinding().tvText.setOnClickListener {
            i++
            getBinding().tvText.text = "测试  测试  测试:$i applicationId: ${MasterApplication.instance.applicationId}"
        }
    }

}



