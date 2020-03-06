package com.lengjiye.code.me.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lengjiye.base.BaseActivity
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityCollectArticleBinding
import com.lengjiye.code.home.adapter.HomeFragmentAdapter
import com.lengjiye.code.home.bean.HomeBean
import com.lengjiye.code.me.viewmodel.MeCollectViewModel
import com.lengjiye.code.utils.*
import com.lengjiye.tools.LogTool
import com.lengjiye.tools.ResTool
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader

class CollectArticleListActivity : BaseActivity<ActivityCollectArticleBinding, MeCollectViewModel>() {

    private val adapter by lazy { HomeFragmentAdapter(this, null) }

    private var page = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_collect_article
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
        mBinding.srlLayout.setEnableLoadMore(false)
        mBinding.rvCoin.layoutManager = LayoutManagerUtils.verticalLinearLayoutManager(this)
        mBinding.rvCoin.addItemDecoration(LayoutManagerUtils.borderDivider(0, ResTool.getDimens(R.dimen.d_4), 0, 0))
        mBinding.rvCoin.adapter = adapter

        mBinding.srlLayout.setOnRefreshListener {
            refresh()
        }

        mBinding.srlLayout.setOnLoadMoreListener {
            loadData()
        }

        adapter.setOnItemClickListener { v, position, item ->
            item?.let {
                ActivityUtil.startWebViewActivity(this, it.link)
            }
        }

        adapter.collectClickListener { view, position, item ->
            item?.let {
                if (AccountUtil.isNoLogin()) {
                    ActivityUtil.startLoginActivity(this)
                    return@let
                }

                mViewModel.unMyCollectArticle(this, it.id, it.originId ?: -1)

                adapter.getItems().remove(it)
                adapter.notifyItemRemoved(position)
                if (position != adapter.itemCount) {
                    adapter.notifyItemRangeChanged(position, adapter.itemCount - position)
                }
            }
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
            mBinding.srlLayout.setEnableLoadMore(true)
            if (page == 0) {
                mBinding.srlLayout.finishRefresh()
                adapter.removeAll()
            } else {
                mBinding.srlLayout.finishLoadMore()
            }
            page++
            adapter.addAll(addCollectTag(list))
        })


        refresh()
    }

    /**
     * 添加收藏标示
     */
    private fun addCollectTag(lists: List<HomeBean>): MutableList<HomeBean> {
        val newLists = mutableListOf<HomeBean>()
        lists.forEach {
            it.collect = true
            newLists.add(it)
        }
        return newLists
    }

    private fun loadData() {
        mViewModel.getCollectArticleList(this, page)
    }
}