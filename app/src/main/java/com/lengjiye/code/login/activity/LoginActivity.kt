package com.lengjiye.code.login.activity

import android.widget.TextView
import com.lengjiye.base.fragment.ParentFragment
import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.constant.IntentKey
import com.lengjiye.code.constant.LoginActivityType
import com.lengjiye.code.databinding.ActivityLoginBinding
import com.lengjiye.code.login.fragment.LoginFragment
import com.lengjiye.code.login.fragment.RegisterFragment
import com.lengjiye.code.login.viewmodel.LoginViewModel
import com.lengjiye.code.utils.ToolBarUtils

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description: 登录注册
 */
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    // 类型 默认登录
    private var type: Int? = null
    private var mTempFragment: ParentFragment<*, *>? = null
    private var titleView: TextView? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initToolBar() {
        super.initToolBar()
        ToolBarUtils.Builder(findViewById(R.id.toolbar)).setType(ToolBarUtils.NORMAL_TYPE)
            .setBackRes(R.drawable.ic_back_ffffff_24dp).setNormalTitleColor(R.color.c_ff).setBackListener {
                finish()
            }.builder()

        titleView = ToolBarUtils.getNormalTitle(findViewById(R.id.toolbar))
    }

    override fun initData() {
        super.initData()
        type = intent.extras?.getInt(IntentKey.KEY_TYPE, LoginActivityType.TYPE_1)
        selectFragment()
    }

    private fun switchFragment(): ParentFragment<*, *> {
        return if (type == LoginActivityType.TYPE_1) {
            titleView?.setText(R.string.s_25)
            LoginFragment.newInstance()
        } else {
            titleView?.setText(R.string.s_26)
            RegisterFragment.newInstance()
        }
    }

    fun selectFragment(type: Int = LoginActivityType.TYPE_1) {
        this.type = type
        val fragment = switchFragment()
        if (mTempFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fl_layout, fragment)
                .show(fragment).commit()
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.fl_layout, fragment).hide(mTempFragment!!)
                .show(fragment).commit()
        }
        mTempFragment = fragment
    }
}
