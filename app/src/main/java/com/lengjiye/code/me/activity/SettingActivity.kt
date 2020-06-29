package com.lengjiye.code.me.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.databinding.ActivitySettingBinding
import com.lengjiye.code.login.viewmodel.LoginViewModel
import com.lengjiye.code.utils.ToolBarUtils
import com.lengjiye.tools.toast
import com.lengjiye.tools.ResTool

class SettingActivity : BaseActivity<ActivitySettingBinding, LoginViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initToolBar() {
        super.initToolBar()
        ToolBarUtils.Builder(findViewById(R.id.toolbar)).setType(ToolBarUtils.NORMAL_TYPE)
            .setBackRes(R.drawable.ic_back_ffffff_24dp)
            .setNormalTitle(R.string.s_27).setNormalTitleColor(R.color.c_ff).setBackListener {
                finish()
            }.builder()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.hnLogout.setOnClickListener {
            mViewModel.logout()
        }
    }

    override fun initLiveDataListener() {
        super.initLiveDataListener()
        mViewModel.logoutSuc.observe(this, Observer {
            if (it) {
                ResTool.getString(R.string.s_29).toast()
                finish()
            }
        })
    }
}