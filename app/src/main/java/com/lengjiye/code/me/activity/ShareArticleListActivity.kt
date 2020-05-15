package com.lengjiye.code.me.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.databinding.ActivityShareArticleBinding
import com.lengjiye.code.home.adapter.HomeFragmentAdapter
import com.lengjiye.code.me.viewmodel.MeShareViewModel
import com.lengjiye.code.utils.ToolBarUtils
import com.lengjiye.code.utils.toast
import com.lengjiye.tools.ResTool
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader

class ShareArticleListActivity : BaseActivity<ActivityShareArticleBinding, MeShareViewModel>() {

    private val adapter by lazy { HomeFragmentAdapter(this, null) }

    private var page = 0
    private var userId = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_share_article
    }

    override fun initToolBar() {
        super.initToolBar()
        ToolBarUtils.Builder(findViewById(R.id.toolbar)).setType(ToolBarUtils.NORMAL_TYPE)
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

        mBinding.rvList.layoutManager = LinearLayoutManager(this)
        mBinding.rvList.adapter = adapter

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

    override fun initIntent(savedInstanceState: Bundle?) {
        super.initIntent(savedInstanceState)
        intent.extras?.let {
            userId = it.getInt(ConstantKey.KEY_USER_ID)
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.shareArticles.observe(this, Observer {
            val list = it.shareArticles.datas
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
        mViewModel.getUserShareArticles(userId, page)
    }
}