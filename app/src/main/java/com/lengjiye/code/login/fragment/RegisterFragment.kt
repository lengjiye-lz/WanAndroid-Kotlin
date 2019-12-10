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
import com.lengjiye.code.databinding.FragmentMeBinding
import com.lengjiye.code.databinding.FragmentRegisterBinding
import com.lengjiye.code.login.activity.LoginActivity
import com.lengjiye.code.login.viewmodel.LoginViewModel
import com.lengjiye.code.me.viewmodel.MeViewModel
import com.lengjiye.code.utils.AccountUtil
import com.lengjiye.code.utils.toast
import com.lengjiye.tools.ResTool

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description: 注册
 */
class RegisterFragment : BaseFragment<FragmentRegisterBinding, LoginViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_register
    }

    override fun getViewModel(): LoginViewModel {
        return LoginViewModel(CodeApplication.instance)
    }

    override fun bindViewModel() {
        mBinding.viewModel = mViewModel
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment().apply {}
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.ivRegister.setOnClickListener {
            val name = mBinding.detName.getText()
            val pass = mBinding.detPass.getText()
            val rePass = mBinding.detRePass.getText()
            mViewModel.register(this, name, pass, rePass)
        }

        mBinding.tvGoLogin.setOnClickListener {
            (getBaseActivity() as LoginActivity).selectFragment(LoginActivityType.TYPE_1)
        }
    }

    override fun initData() {
        super.initData()

        initValue()

        mViewModel.registerSuc.observe(this, Observer {
            getBaseActivity().finish()
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

            ErrorCode.rePassError -> {
                ResTool.getString(R.string.s_11).toast()
            }
        }
    }

    private fun setTextColor() {
        val text = ResTool.getString(R.string.s_13)
        val spText = SpannableString(text)
        spText.setSpan(ForegroundColorSpan(ResTool.getColor(R.color.colorPrimary)), 5, text.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        mBinding.tvGoLogin.text = spText
    }
}