package com.lengjiye.code.login.fragment

import android.os.Bundle
import com.lengjiye.base.fragment.BaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.databinding.FragmentLoginBinding
import com.lengjiye.code.login.viewmodel.LoginViewModel

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description: 登录
 */
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun getViewModel(): LoginViewModel {
        return LoginViewModel(CodeApplication.instance)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): FragmentLoginBinding {
        return mBinding
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment().apply {}
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.ivLogin.setOnClickListener {
            mViewModel.login(this, "lengjiye", "lengjiye123")
        }

    }
}