package com.lengjiye.code.share.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lengjiye.base.fragment.LazyParentFragment
import com.lengjiye.code.R
import com.lengjiye.code.constant.HomeFragmentAdapterType
import com.lengjiye.code.databinding.FragmentShareBinding
import com.lengjiye.code.home.adapter.HomeFragmentAdapter
import com.lengjiye.code.share.viewmodel.ShareViewModel
import com.lengjiye.code.utils.AccountUtils
import com.lengjiye.code.utils.ActivityUtils
import com.lengjiye.code.utils.LayoutManagerUtils
import com.lengjiye.code.utils.toast
import com.lengjiye.tools.ResTool
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader

/**
 * @Author: lz
 * @Date: 2019-12-27
 * @Description: 广场
 */
class ShareFragment : LazyParentFragment<FragmentShareBinding, ShareViewModel>() {

    private val adapter: HomeFragmentAdapter by lazy { HomeFragmentAdapter(getBaseActivity(), null) }
    private var pager = 0

    override fun getLayoutId(): Int {
        return R.layout.fragment_share
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.srlLayout.setRefreshHeader(MaterialHeader(getBaseActivity()))
        mBinding.srlLayout.setRefreshFooter(BallPulseFooter(getBaseActivity()))

        mBinding.rlList.layoutManager = LayoutManagerUtils.verticalLinearLayoutManager(getBaseActivity())
        mBinding.rlList.addItemDecoration(LayoutManagerUtils.borderDivider(0, ResTool.getDimens(R.dimen.d_4), 0, 0))
        adapter.type = HomeFragmentAdapterType.TYPE_2
        mBinding.rlList.adapter = adapter

        mBinding.srlLayout.setOnRefreshListener {
            refresh()
        }

        mBinding.srlLayout.setOnLoadMoreListener {
            loadData()
        }

        adapter.setOnItemClickListener { v, position, item ->
            item?.let {
                ActivityUtils.startWebViewActivity(this.getBaseActivity(), it.link)
            }
        }

        adapter.collectClickListener { view, position, item ->
            item?.let {
                if (AccountUtils.isNoLogin()) {
                    ActivityUtils.startLoginActivity(getBaseActivity())
                    return@let
                }

                if (it.collect) {
                    mViewModel.unCollectArticle(it.id)
                } else {
                    mViewModel.collectAddArticle(it.id)
                }

                it.collect = !it.collect
                adapter.getItems().set(position, it)
                adapter.notifyItemChanged(position)
            }
        }
    }

    override fun initLiveDataListener() {
        super.initLiveDataListener()
        mViewModel.userArticleList.observe(this, Observer {
            if (pager == 0) {
                mBinding.srlLayout.finishRefresh()
                if (it.datas.isEmpty()) {
                    // TODO 显示错误界面
                } else {
                    adapter.removeAll()
                    adapter.addAll(it.datas.toMutableList())
                    adapter.notifyDataSetChanged()
                }
            } else {
                mBinding.srlLayout.finishLoadMore()
                if (it.datas.isEmpty()) {
                    ResTool.getString(R.string.s_5).toast()
                    return@Observer
                } else {
                    adapter.addAll(it.datas.toMutableList())
                    adapter.notifyItemRangeChanged(adapter.itemCount, it.datas.size)
                }
            }
            pager++
        })
    }


    private fun refresh() {
        pager = 0
        loadData()
    }

    override fun loadData() {
        mViewModel.getUserArticleList(pager)
    }


}