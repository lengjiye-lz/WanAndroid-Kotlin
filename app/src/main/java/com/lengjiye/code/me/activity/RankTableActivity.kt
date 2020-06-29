package com.lengjiye.code.me.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.databinding.ActivityRankTableBinding
import com.lengjiye.code.me.adapter.RankTableAdapter
import com.lengjiye.code.me.viewmodel.MeViewModel
import com.lengjiye.code.utils.ToolBarUtils
import com.lengjiye.tools.toast
import com.lengjiye.tools.ResTool
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader

/**
 * 积分榜
 */
class RankTableActivity : BaseActivity<ActivityRankTableBinding, MeViewModel>() {

    private val adapter by lazy { RankTableAdapter(this, null) }

    private var page = 1

    override fun getLayoutId(): Int {
        return R.layout.activity_rank_table
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.srlLayout.setRefreshHeader(MaterialHeader(this))
        mBinding.srlLayout.setRefreshFooter(BallPulseFooter(this))

        mBinding.rvRank.layoutManager = LinearLayoutManager(this)
        mBinding.rvRank.adapter = adapter

        mBinding.srlLayout.setOnRefreshListener {
            refresh()
        }

        mBinding.srlLayout.setOnLoadMoreListener {
            loadData()
        }
    }

    override fun initToolBar() {
        super.initToolBar()
        ToolBarUtils.Builder(findViewById(R.id.toolbar)).setType(ToolBarUtils.NORMAL_TYPE)
            .setBackRes(R.drawable.ic_back_ffffff_24dp)
            .setNormalTitle(R.string.s_24).setNormalTitleColor(R.color.c_ff).setBackListener {
                finish()
            }.builder()
    }

    private fun refresh() {
        page = 1
        loadData()
    }

    override fun initData() {
        super.initData()
        mViewModel.rankTable.observe(this, Observer {
            val list = it.datas
            if (list.isEmpty()) {
                if (page == 1) {
                    // TODO 显示错误界面
                    mBinding.srlLayout.finishRefresh()
                } else {
                    mBinding.srlLayout.finishLoadMore()
                    ResTool.getString(R.string.s_5).toast()
                }
                return@Observer
            }

            if (page == 1) {
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
        mViewModel.getCoinRank(page)
    }
}