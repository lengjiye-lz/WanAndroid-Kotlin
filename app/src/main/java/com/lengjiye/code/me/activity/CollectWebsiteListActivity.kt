package com.lengjiye.code.me.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lengjiye.base.BaseActivity
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityCollectWebsiteBinding
import com.lengjiye.code.me.adapter.CollectWebsiteListAdapter
import com.lengjiye.code.me.viewmodel.MeViewModel
import com.lengjiye.code.utils.ActivityUtil
import com.lengjiye.code.utils.ToolBarUtil
import com.lengjiye.code.utils.toast
import com.lengjiye.tools.ResTool
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader

/**
 * 我收藏的网站的列表
 */
class CollectWebsiteListActivity : BaseActivity<ActivityCollectWebsiteBinding, MeViewModel>() {

    private val adapter by lazy { CollectWebsiteListAdapter(this, null) }


    override fun getLayoutId(): Int {
        return R.layout.activity_collect_website
    }

    override fun bindViewModel() {
        mBinding.viewModel = mViewModel
    }

    override fun getViewModel(): MeViewModel {
        return MeViewModel(application)
    }

    override fun initToolBar() {
        super.initToolBar()
        ToolBarUtil.Builder(findViewById(R.id.toolbar)).setType(ToolBarUtil.NORMAL_TYPE)
            .setBackRes(R.drawable.ic_back_ffffff_24dp).setNormalTitleColor(R.color.c_ff)
            .setNormalTitle(R.string.s_21)
            .setBackListener {
                finish()
            }.builder()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.srlLayout.setRefreshHeader(MaterialHeader(this))

        mBinding.rvCoin.layoutManager = LinearLayoutManager(this)
        mBinding.rvCoin.adapter = adapter

        mBinding.srlLayout.setOnRefreshListener {
            loadData()
        }

        adapter.setOnItemClickListener { v, position, item ->
            val url = item?.link
            url?.let { ActivityUtil.startWebViewActivity(this, it) }
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.websiteList.observe(this, Observer {
            val list = it
            mBinding.srlLayout.finishRefresh()
            if (list.isEmpty()) {
                // TODO 显示错误界面
                return@Observer
            }

            mBinding.srlLayout.finishRefresh()
            adapter.removeAll()
            adapter.addAll(list.toMutableList())
        })
        loadData()
    }

    private fun loadData() {
        mViewModel.getCollectWebsiteList(this)
    }
}