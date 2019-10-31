package com.lengjiye.code.test.activity

import android.os.Bundle
import com.lengjiye.base.BaseActivity
import com.lengjiye.base.application.MasterApplication
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityBesselBinding
import com.lengjiye.code.test.viewmode.BesselViewMode

/**
 * 贝塞尔曲线测试 demo
 */
open class BesselActivity : BaseActivity<ActivityBesselBinding, BesselViewMode>() {

    private var i: Int = 0

    override fun getViewModel(): BesselViewMode {
        return BesselViewMode(application)
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
    override fun getBinding(): ActivityBesselBinding {
        return mBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
        getBinding().tvText.text = "测试  测试  测试"
        getBinding().tvText.setOnClickListener {
            i++
            getBinding().tvText.text = "测试  测试  测试:$i applicationId: ${MasterApplication.getInstance().applicationId()}"
        }
    }

}



