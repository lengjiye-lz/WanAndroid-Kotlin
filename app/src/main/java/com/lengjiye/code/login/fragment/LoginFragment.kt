package com.lengjiye.code.login.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lengjiye.base.constant.ErrorCode
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
            val name = mBinding.detName.getText()
            val pass = mBinding.detPass.getText()
            mViewModel.login(this, name, pass)
        }

        mBinding.tvRegister.setOnClickListener {

        }
    }

    override fun initData() {
        super.initData()

        mViewModel.loginSuc.observe(this, Observer {
            if (it) {
                getBaseActivity().finish()
            }
        })

        mViewModel.errorCode.observe(this, Observer {
            error(it)
        })
    }

    /**
     * 统一处理error
     */
    private fun error(error: Int) {
        when (error) {
            ErrorCode.nameError -> {

            }
        }
    }


}