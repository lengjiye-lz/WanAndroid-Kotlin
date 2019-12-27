package com.lengjiye.code.me.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lengjiye.base.BaseActivity
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityCoinListBinding
import com.lengjiye.code.databinding.ActivityCollectArticleBinding
import com.lengjiye.code.databinding.ActivityRankTableBinding
import com.lengjiye.code.databinding.ActivityShareArticleBinding
import com.lengjiye.code.me.adapter.CoinListAdapter
import com.lengjiye.code.me.adapter.CollectArticleListAdapter
import com.lengjiye.code.me.adapter.CollectWebsiteListAdapter
import com.lengjiye.code.me.adapter.RankTableAdapter
import com.lengjiye.code.me.viewmodel.MeCollectViewModel
import com.lengjiye.code.utils.ToolBarUtil
import com.lengjiye.code.utils.toast
import com.lengjiye.tools.ResTool
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader

class ShareArticleListActivity : BaseActivity<ActivityShareArticleBinding, MeCollectViewModel>() {

    private val adapter by lazy { CollectArticleListAdapter(this, null) }

    private var page = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_share_article
    }

    override fun bindViewModel() {
        mBinding.viewModel = mViewModel
    }

    override fun getViewModel(): MeCollectViewModel {
        return MeCollectViewModel(application)
    }

    override fun initToolBar() {
        super.initToolBar()
        ToolBarUtil.Builder(findViewById(R.id.toolbar)).setType(ToolBarUtil.NORMAL_TYPE)
            .setBackRes(R.drawable.ic_back_ffffff_24dp).setNormalTitleColor(R.color.c_ff)
            .setNormalTitle(R.string.s_20)
            .setBackListener {
                finish()
            }.builder()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.srlLayout.setRefreshHeader(MaterialHeader(this))
        mBinding.srlLayout.setRefreshFooter(BallPulseFooter(this))

        mBinding.rvCoin.layoutManager = LinearLayoutManager(this)
        mBinding.rvCoin.adapter = adapter

        mBinding.srlLayout.setOnRefreshListener {
            refresh()
        }

        mBinding.srlLayout.setOnLoadMoreListener {
            loadData()
        }
    }

    private fun refresh() {
        page = 0
        loadData()
    }

    override fun initData() {
        super.initData()
        mViewModel.articleList.observe(this, Observer {
            val list = it.datas
            if (list.isEmpty()) {
                if (page == 0) {
                    // TODO 显示错误界面
                    mBinding.srlLayout.finishRefresh()
                } else {
                    mBinding.srlLayout.finishLoadMore()
                    ResTool.getString(R.string.s_5).toast()
                }
                return@Observer
            }

            if (page == 0) {
                mBinding.srlLayout.finishRefresh()
                adapter.removeAll()
            } else {
                mBinding.srlLayout.finishLoadMore()
            }
            page++
            adapter.addAll(list.toMutableList())
        })


        refresh()
    }

    private fun loadData() {
        mViewModel.getCollectArticleList(this, page)
    }
}