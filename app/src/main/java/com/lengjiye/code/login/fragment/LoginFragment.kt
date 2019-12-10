package com.lengjiye.code.login.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.Observer
import com.lengjiye.base.constant.ErrorCode
import com.lengjiye.base.fragment.BaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.constant.LoginActivityType
import com.lengjiye.code.databinding.FragmentLoginBinding
import com.lengjiye.code.login.activity.LoginActivity
import com.lengjiye.code.login.viewmodel.LoginViewModel
import com.lengjiye.code.utils.AccountUtil
import com.lengjiye.code.utils.toast
import com.lengjiye.tools.ResTool

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
        mBinding.viewModel = mViewModel
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

        mBinding.tvGoRegister.setOnClickListener {
            (getBaseActivity() as LoginActivity).selectFragment(LoginActivityType.TYPE_2)
        }
    }

    override fun initData() {
        super.initData()
        initValue()
        mViewModel.loginSuc.observe(this, Observer {
            if (it) {
                getBaseActivity().finish()
            }
        })

        mViewModel.errorCode.observe(this, Observer {
            error(it)
        })
    }

    private fun initValue() {
        val name = AccountUtil.getUserName()
        mBinding.detName.setEditText(name)
        setTextColor()
    }

    private fun setTextColor() {
        val text = ResTool.getString(R.string.s_6)
        val spText = SpannableString(text)
        spText.setSpan(ForegroundColorSpan(ResTool.getColor(R.color.colorPrimary)), 5, text.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        mBinding.tvGoRegister.text = spText
    }

    /**
     * 统一处理error
     */
    private fun error(error: Int) {
        when (error) {
            ErrorCode.nameError -> {
                ResTool.getString(R.string.s_9).toast()
            }

            ErrorCode.passError -> {
                ResTool.getString(R.string.s_10).toast()
            }
        }
    }
}