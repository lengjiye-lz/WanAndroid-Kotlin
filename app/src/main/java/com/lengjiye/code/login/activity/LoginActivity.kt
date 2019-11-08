package com.lengjiye.code.login.activity

import android.os.Bundle
import com.lengjiye.base.BaseActivity
import com.lengjiye.base.fragment.BaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.constant.LoginActivityType
import com.lengjiye.code.databinding.ActivityLoginBinding
import com.lengjiye.code.login.fragment.LoginFragment
import com.lengjiye.code.login.fragment.RegisterFragment
import com.lengjiye.code.login.viewmodel.LoginViewModel

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description: 登录注册
 */
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    // 类型 默认登录
    private var type: Int? = null
    private var mTempFragment: BaseFragment<*, *>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModel(): LoginViewModel {
        return LoginViewModel(application)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    /**
     * 获取 mBinding
     */
    override fun getBinding(): ActivityLoginBinding {
        return mBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

    }

    override fun initData() {
        super.initData()
        type = intent.extras?.getInt(ConstantKey.KEY_TYPE, LoginActivityType.TYPE_1)
        selectFragment()
    }

    private fun switchFragment(): BaseFragment<*, *> {
        return if (type == LoginActivityType.TYPE_1) {
            LoginFragment.newInstance()
        } else {
            RegisterFragment.newInstance()
        }
    }

    private fun selectFragment(type: Int = LoginActivityType.TYPE_1) {
        this.type = type
        val fragment = switchFragment()
        if (mTempFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.f_container, fragment)
                .show(fragment).commit()
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.f_container, fragment).hide(mTempFragment!!)
                .show(fragment).commit()
        }
        mTempFragment = fragment
    }
}
