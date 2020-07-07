package com.lengjiye.code.share.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lengjiye.base.fragment.LazyParentFragment
import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.constant.HomeFragmentAdapterType
import com.lengjiye.code.databinding.FragmentShareBinding
import com.lengjiye.code.home.adapter.HomeFragmentAdapter
import com.lengjiye.code.share.viewmodel.ShareViewModel
import com.lengjiye.code.utils.AccountUtils
import com.lengjiye.code.utils.ActivityUtils
import com.lengjiye.code.utils.LayoutManagerUtils
import com.lengjiye.tools.toast
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
            lazyData()
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

    override fun lazyLiveDataListener() {
        super.lazyLiveDataListener()
        mViewModel.shareRefreshList.observe(this, Observer {
            if (it.first) {
                // 缓存  如果已经有数据就不再处理
                if (adapter.itemCount <= 0) {
                    adapter.removeAll()
                    adapter.addAll(it.second.datas.toMutableList())
                    adapter.notifyDataSetChanged()
                }
            } else {
                // 网络数据
                mBinding.srlLayout.finishRefresh()
                adapter.removeAll()
                adapter.addAll(it.second.datas.toMutableList())
                adapter.notifyDataSetChanged()
            }
            pager = it.second.curPage
        })

        mViewModel.shareMoreList.observe(this, Observer {
            mBinding.srlLayout.finishLoadMore()
            if (it.datas.isEmpty()) {
                ResTool.getString(R.string.s_5).toast()
                return@Observer
            } else {
                adapter.addAll(it.datas.toMutableList())
                adapter.notifyItemRangeChanged(adapter.itemCount, it.datas.size)
            }
            pager = it.curPage
        })
    }

    private fun loadData() {
        mViewModel.getShareMoreList(pager)
    }

    override fun lazyData() {
        if (getBaseActivity() is BaseActivity) {
            (getBaseActivity() as BaseActivity).goScrollToTopInterfaceAnimation(mBinding.rlList, 0)
        }
        mBinding.srlLayout.autoRefreshAnimationOnly()
        mViewModel.getShareRefreshList()
    }
}