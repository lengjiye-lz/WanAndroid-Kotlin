package com.lengjiye.code.login.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.Observer
import com.lengjiye.base.fragment.ParentFragment
import com.lengjiye.code.R
import com.lengjiye.code.constant.LoginActivityType
import com.lengjiye.code.databinding.FragmentLoginBinding
import com.lengjiye.code.login.activity.LoginActivity
import com.lengjiye.code.login.viewmodel.LoginViewModel
import com.lengjiye.code.utils.AccountUtils
import com.lengjiye.code.utils.toast
import com.lengjiye.network.exception.ApiException
import com.lengjiye.tools.ResTool

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description: 登录
 */
class LoginFragment : ParentFragment<FragmentLoginBinding, LoginViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
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
            mViewModel.login(name, pass)
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
                ResTool.getString(R.string.s_30).toast()
                getBaseActivity().finish()
            }
        })

        mViewModel.errorCode.observe(this, Observer {
            if (it is ApiException) {
                it.mErrorMsg?.toast()
            }
        })
    }

    private fun initValue() {
        val name = AccountUtils.getUserName()
        mBinding.detName.setEditText(name)
        setTextColor()
    }

    private fun setTextColor() {
        val text = ResTool.getString(R.string.s_6)
        val spText = SpannableString(text)
        spText.setSpan(ForegroundColorSpan(ResTool.getColor(R.color.colorPrimary)), 5, text.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        mBinding.tvGoRegister.text = spText
    }
}