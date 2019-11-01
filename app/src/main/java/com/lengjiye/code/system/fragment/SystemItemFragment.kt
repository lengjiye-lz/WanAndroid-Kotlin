package com.lengjiye.code.system.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.lengjiye.base.LazyBaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.databinding.SystemItemFragmentBinding
import com.lengjiye.code.home.adapter.HomeFragmentAdapter
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.code.system.viewmodel.SystemViewModel
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader

class SystemItemFragment : LazyBaseFragment<SystemItemFragmentBinding, SystemViewModel>() {

    private var treeBean: TreeBean? = null
    private var secondTree: TreeBean? = null
    private var pager = 0
    private val adapter by lazy { HomeFragmentAdapter(getBaseActivity(), null) }

    override fun getLayoutId(): Int {
        return R.layout.system_item_fragment
    }

    override fun getViewModel(): SystemViewModel {
        return SystemViewModel(getBaseActivity().application)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): SystemItemFragmentBinding {
        return mBinding
    }

    companion object {
        @JvmStatic
        fun newInstance(extras: Bundle?) = SystemItemFragment().apply {
            arguments = extras
        }
    }

    override fun loadData() {
        refresh()
    }

    private fun load() {
        mViewModel.getTreeArticleList(this, pager, secondTree?.id ?: 0)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        mBinding.srlLayout.setRefreshHeader(MaterialHeader(getBaseActivity()))
        mBinding.srlLayout.setRefreshFooter(BallPulseFooter(getBaseActivity()))

        mBinding.rvView.layoutManager = LinearLayoutManager(getBaseActivity())
        mBinding.rvView.adapter = adapter

        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val position = p0?.position
                secondTree = position?.let { treeBean?.children?.get(it) }
                refresh()
            }
        })

        mBinding.srlLayout.setOnRefreshListener {
            load()
        }

        mBinding.srlLayout.setOnLoadMoreListener {
            load()
        }
    }

    override fun initData() {
        super.initData()
        treeBean = arguments?.getParcelable(ConstantKey.KEY_OBJECT)
        if (treeBean == null) {
            return
        }
        initTitle(treeBean)

        mViewModel.articleBean.observe(this, Observer {
            mBinding.srlLayout.finishLoadMoreWithNoMoreData()
            mBinding.srlLayout.finishRefreshWithNoMoreData()
            if (pager == 0) {
                adapter.removeAll()
            }
            adapter.addAll(it.datas.toMutableList())
            pager = it.curPage + 1
        })

    }

    private fun refresh() {
        pager = 0
        mBinding.srlLayout.autoRefresh()
    }

    private fun initTitle(treeBean: TreeBean?) {
        treeBean?.let {
            val trees = it.children
            if (trees.isEmpty()) {
                mBinding.tabLayout.visibility = View.GONE
                return@let
            }
            secondTree = treeBean.children[0]
            var tab: TabLayout.Tab
            trees.forEachIndexed { index, tree ->
                tab = mBinding.tabLayout.newTab()
                tab.tag = index
                tab.text = tree.name
                mBinding.tabLayout.addTab(tab)
            }
        }
    }
}