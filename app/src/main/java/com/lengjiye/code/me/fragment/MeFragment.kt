package com.lengjiye.code.me.fragment

import android.os.Bundle
import android.view.View
import com.lengjiye.base.fragment.BaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.databinding.FragmentMeBinding
import com.lengjiye.code.login.activity.LoginActivity
import com.lengjiye.code.me.viewmodel.MeViewModel
import com.lengjiye.code.utils.AccountUtil
import com.lengjiye.code.utils.ActivityUtil
import com.lengjiye.code.utils.startActivity

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeFragment : BaseFragment<FragmentMeBinding, MeViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun getViewModel(): MeViewModel {
        return MeViewModel(CodeApplication.instance)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): FragmentMeBinding {
        return mBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

    }

    override fun initData() {
        super.initData()

    }

    /**
     * 请求接口
     */
    private fun loadData() {
        if (!AccountUtil.isLogin()) {
            mBinding.tvMeRank.visibility = View.GONE
            return
        }


    }
}