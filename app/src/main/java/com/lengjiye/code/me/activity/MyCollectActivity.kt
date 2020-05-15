package com.lengjiye.code.me.activity

import android.os.Bundle
import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.databinding.ActivityMyCollectBinding
import com.lengjiye.code.me.viewmodel.MeCollectViewModel
import com.lengjiye.code.utils.ActivityUtils
import com.lengjiye.code.utils.ToolBarUtils

/**
 * 我的收藏
 */
class MyCollectActivity : BaseActivity<ActivityMyCollectBinding, MeCollectViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_my_collect
    }

    override fun initToolBar() {
        super.initToolBar()
        ToolBarUtils.Builder(findViewById(R.id.toolbar)).setType(ToolBarUtils.NORMAL_TYPE)
            .setBackRes(R.drawable.ic_back_ffffff_24dp).setNormalTitleColor(R.color.c_ff)
            .setNormalTitle(R.string.s_16)
            .setBackListener {
                finish()
            }.builder()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        mBinding.hnArticleList.setOnClickListener {
            ActivityUtils.startCollectArticleListActivity(this)
        }

        mBinding.hnWebsiteList.setOnClickListener {
            ActivityUtils.startCollectWebsiteListActivity(this)
        }
    }
}