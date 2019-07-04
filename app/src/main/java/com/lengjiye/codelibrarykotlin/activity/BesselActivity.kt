package com.lengjiye.codelibrarykotlin.activity

import androidx.databinding.ViewDataBinding
import com.lengjiye.base.BaseActivity
import com.lengjiye.base.viewmode.BaseViewMode
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.viewmode.BesselViewMode

/**
 * 贝塞尔曲线测试 demo
 */
class BesselActivity<out T : ViewDataBinding, out M : BaseViewMode> : BaseActivity<T, M>() {


    override fun getViewModel(): M {
        return BesselViewMode()
    }

    override fun getViewDataBinding(): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_bessel
    }

}



