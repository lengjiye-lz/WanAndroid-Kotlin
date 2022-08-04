package com.lengjiye.code.system.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.lengjiye.base.fragment.ViewPagerLazyParentFragment
import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.constant.IntentKey
import com.lengjiye.code.constant.HomeFragmentAdapterType
import com.lengjiye.code.databinding.FragmentSystemItemBinding
import com.lengjiye.code.home.adapter.HomeFragmentAdapter
import com.lengjiye.code.system.viewmodel.SystemViewModel
import com.lengjiye.code.utils.AccountUtils
import com.lengjiye.code.utils.ActivityUtils
import com.lengjiye.code.utils.LayoutManagerUtils
import com.lengjiye.room.entity.SystemTreeBean
import com.lengjiye.tools.toast
import com.lengjiye.room.entity.SystemTreeEntity
import com.lengjiye.tools.ResTool
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader

class SystemFragmentItem : ViewPagerLazyParentFragment<FragmentSystemItemBinding, SystemViewModel>() {

    private var treeBean: SystemTreeEntity? = null
    private var secondTree: SystemTreeBean? = null
    private var pager = 0
    private val adapter by lazy { HomeFragmentAdapter(getBaseActivity(), null) }

    // 第一次不加载数据
    private var isFirst = false

    // 是不是要加载缓存
    private var isRoom = true
    private var selectPosition: Int? = 0

    override fun getLayoutId(): Int {
        return R.layout.fragment_system_item
    }

    companion object {
        @JvmStatic
        fun newInstance(extras: Bundle?) = SystemFragmentItem().apply {
            arguments = extras
        }
    }

    override fun lazyData() {
        if (isRoom) mViewModel.getTreeArticleList2Room()
        refresh()
    }

    private fun loadMore() {
        mViewModel.getTreeArticleList(pager, secondTree?.id ?: 0)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        mBinding.srlLayout.setRefreshHeader(MaterialHeader(getBaseActivity()))
        mBinding.srlLayout.setRefreshFooter(BallPulseFooter(getBaseActivity()))

        mBinding.rvView.layoutManager = LayoutManagerUtils.verticalLinearLayoutManager(getBaseActivity())
        mBinding.rvView.addItemDecoration(LayoutManagerUtils.borderDivider(0, ResTool.getDimens(R.dimen.d_4), 0, 0))
        adapter.type = HomeFragmentAdapterType.TYPE_2
        mBinding.rvView.adapter = adapter

        setDivider()

        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                selectPosition = p0?.position
                secondTree = selectPosition?.let {
                    treeBean?.childrens?.get(it)
                }
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

    override fun lazyView() {
        super.lazyView()
        isRoom = arguments?.getInt(IntentKey.KEY_POSITION, -1) == 0 && selectPosition == 0
        treeBean = arguments?.getParcelable(IntentKey.KEY_OBJECT)
        if (treeBean == null) {
            return
        }
        initTitle(treeBean)
    }

    override fun lazyLiveDataListener() {
        super.lazyLiveDataListener()
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

    fun refresh() {
        pager = 0
        if (getBaseActivity() is BaseActivity) {
            (getBaseActivity() as BaseActivity).goScrollToTopInterfaceAnimation(mBinding.rvView, 0)
        }
        mBinding.srlLayout.autoRefreshAnimationOnly()
        isRoom = false
        loadMore()
    }

    private fun initTitle(treeBean: SystemTreeEntity?) {
        treeBean?.let {
            val trees = it.childrens
            if (trees?.isEmpty() == true) {
                mBinding.tabLayout.visibility = View.GONE
                return@let
            }
            secondTree = treeBean.childrens?.get(0)
            var tab: TabLayout.Tab
            trees?.forEachIndexed { index, tree ->
                tab = mBinding.tabLayout.newTab()
                tab.tag = index
                tab.text = tree.name
                mBinding.tabLayout.addTab(tab)
            }
        }

        mBinding.tabLayout.getTabAt(0)?.select()
    }

    @SuppressLint("ResourceType")
    private fun setDivider() {
        val linearLayout = mBinding.tabLayout.getChildAt(0) as LinearLayout
        linearLayout.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        linearLayout.dividerDrawable = Drawable.createFromXml(resources, resources.getXml(R.drawable.tag_linearlayout_vertical_divider))
        linearLayout.dividerPadding = ResTool.getDimens(R.dimen.d_16)
    }
}