package com.lengjiye.code.me.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lengjiye.base.activity.ParentActivity
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityCollectWebsiteBinding
import com.lengjiye.code.me.adapter.CollectWebsiteListAdapter
import com.lengjiye.code.me.viewmodel.MeCollectViewModel
import com.lengjiye.code.utils.AccountUtil
import com.lengjiye.code.utils.ActivityUtil
import com.lengjiye.code.utils.LayoutManagerUtils
import com.lengjiye.code.utils.ToolBarUtil
import com.lengjiye.tools.ResTool
import com.scwang.smart.refresh.header.MaterialHeader

/**
 * 我收藏的网站的列表
 */
class CollectWebsiteListActivity : ParentActivity<ActivityCollectWebsiteBinding, MeCollectViewModel>() {

    private val adapter by lazy { CollectWebsiteListAdapter(this, null) }


    override fun getLayoutId(): Int {
        return R.layout.activity_collect_website
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

        mBinding.rvCoin.layoutManager = LayoutManagerUtils.verticalLinearLayoutManager(this)
        mBinding.rvCoin.addItemDecoration(LayoutManagerUtils.borderDivider(0, ResTool.getDimens(R.dimen.d_4), 0, 0))

        mBinding.rvCoin.adapter = adapter

        mBinding.srlLayout.setOnRefreshListener {
            loadData()
        }

        adapter.setOnItemClickListener { v, position, item ->
            val url = item?.link
            url?.let { ActivityUtil.startWebViewActivity(this, it) }
        }

        adapter.collectClickListener { view, position, item ->
            item?.let {
                if (AccountUtil.isNoLogin()) {
                    ActivityUtil.startLoginActivity(this)
                    return@let
                }

                mViewModel.collectDeleteWebsite(it.id)
                adapter.getItems().removeAt(position)
                adapter.notifyItemRemoved(position)
                if (position != adapter.itemCount) {
                    adapter.notifyItemRangeChanged(position, adapter.itemCount - position)
                }
            }
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
        mViewModel.getCollectWebsiteList()
    }
}