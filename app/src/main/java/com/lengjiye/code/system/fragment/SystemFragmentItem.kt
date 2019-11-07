package com.lengjiye.code.system.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.lengjiye.base.fragment.ViewPagerLazyBaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.constant.HomeFragmentAdapterType
import com.lengjiye.code.databinding.FragmentSystemItemBinding
import com.lengjiye.code.home.adapter.HomeFragmentAdapter
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.code.system.viewmodel.SystemViewModel
import com.lengjiye.code.utils.ActivityUtil
import com.lengjiye.code.utils.startActivity
import com.lengjiye.code.utils.toast
import com.lengjiye.code.webview.WebViewActivity
import com.lengjiye.tools.LogTool
import com.lengjiye.tools.ResTool
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader

class SystemFragmentItem : ViewPagerLazyBaseFragment<FragmentSystemItemBinding, SystemViewModel>() {

    private var treeBean: TreeBean? = null
    private var secondTree: TreeBean? = null
    private var pager = 0
    private val adapter by lazy { HomeFragmentAdapter(getBaseActivity(), null) }

    // 第一次不加载数据
    private var isFirst = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_system_item
    }

    override fun getViewModel(): SystemViewModel {
        return SystemViewModel(CodeApplication.instance)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): FragmentSystemItemBinding {
        return mBinding
    }

    companion object {
        @JvmStatic
        fun newInstance(extras: Bundle?) = SystemFragmentItem().apply {
            arguments = extras
        }
    }

    override fun loadData() {
        refresh()
    }

    private fun loadMore() {
        mViewModel.getTreeArticleList(this, pager, secondTree?.id ?: 0)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        mBinding.srlLayout.setRefreshHeader(MaterialHeader(getBaseActivity()))
        mBinding.srlLayout.setRefreshFooter(BallPulseFooter(getBaseActivity()))

        mBinding.rvView.layoutManager = LinearLayoutManager(getBaseActivity())
        adapter.type = HomeFragmentAdapterType.TYPE_2
        mBinding.rvView.adapter = adapter

        setDivider()

        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val position = p0?.position
                secondTree = position?.let { treeBean?.children?.get(it) }
                if (isFirst) {
                    refresh()
                }
                isFirst = true
            }
        })

        mBinding.srlLayout.setOnRefreshListener {
            refresh()
        }

        mBinding.srlLayout.setOnLoadMoreListener {
            loadMore()
        }


        adapter.setOnItemClickListener { v, position, item ->
            item?.let {
                ActivityUtil.startWebViewActivity(this.getBaseActivity(), it.link)
            }
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
            if (it.cid != secondTree?.id) {
                mBinding.srlLayout.finishRefresh()
                mBinding.srlLayout.finishLoadMore()
                return@Observer
            }

            if (pager == 0) {
                mBinding.srlLayout.finishRefresh()
                adapter.removeAll()
            } else {
                mBinding.srlLayout.finishLoadMore()
            }

            if (it.datas.isEmpty()) {
                ResTool.getString(R.string.s_5).toast()
                return@Observer
            }
            adapter.addAll(it.datas.toMutableList())
            pager = it.curPage
        })

    }

    private fun refresh() {
        pager = 0
        mBinding.srlLayout.autoRefreshAnimationOnly()
        loadMore()
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

    @SuppressLint("ResourceType")
    private fun setDivider() {
        val linearLayout = mBinding.tabLayout.getChildAt(0) as LinearLayout
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE)
        linearLayout.setDividerDrawable(Drawable.createFromXml(resources, resources.getXml(R.drawable.tag_linearlayout_vertical_divider)))
        linearLayout.setDividerPadding(ResTool.getDimens(R.dimen.d_16))
    }
}