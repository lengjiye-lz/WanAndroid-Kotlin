package com.lengjiye.code.me.activity

import android.os.Bundle
import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.databinding.ActivityMyShareBinding
import com.lengjiye.code.me.viewmodel.MeShareViewModel
import com.lengjiye.code.utils.AccountUtils
import com.lengjiye.code.utils.ActivityUtils
import com.lengjiye.code.utils.ToolBarUtils

/**
 * 我的分享
 */
class MyShareActivity : BaseActivity<ActivityMyShareBinding, MeShareViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_my_share
    }

    override fun initToolBar() {
        super.initToolBar()
        ToolBarUtils.Builder(findViewById(R.id.toolbar)).setType(ToolBarUtils.NORMAL_TYPE)
            .setBackRes(R.drawable.ic_back_ffffff_24dp).setNormalTitleColor(R.color.c_ff)
            .setNormalTitle(R.string.s_17)
            .setBackListener {
                finish()
            }.builder()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        mBinding.hnArticleList.setOnClickListener {
            ActivityUtils.startShareArticleListActivity(this, AccountUtils.getUserId())
        }

        mBinding.hnProjectList.setOnClickListener {
            ActivityUtils.startCollectWebsiteListActivity(this)
        }
    }
}