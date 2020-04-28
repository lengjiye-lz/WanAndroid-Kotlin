package com.lengjiye.code.project.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lengjiye.base.fragment.ParentFragment
import com.lengjiye.code.R
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.databinding.FragmentProjectItemBinding
import com.lengjiye.code.project.adapter.ProjectFragmentItemAdapter
import com.lengjiye.code.project.viewmodel.ProjectViewModel
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.code.utils.AccountUtil
import com.lengjiye.code.utils.ActivityUtil
import com.lengjiye.code.utils.LayoutManagerUtils
import com.lengjiye.code.utils.toast
import com.lengjiye.tools.ResTool
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description: 项目
 */
class ProjectFragmentItem : ParentFragment<FragmentProjectItemBinding, ProjectViewModel>() {

    private var projectTree: TreeBean? = null
    private var pager = 1
    private var cid = 0
    private val adapter by lazy { ProjectFragmentItemAdapter(getBaseActivity(), null) }
    // 数据只请求一次
    private var isFirst = true

    companion object {
        @JvmStatic
        fun newInstance(extras: Bundle?) = ProjectFragmentItem().apply {
            arguments = extras
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_project_item
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.srlLayout.setRefreshHeader(MaterialHeader(getBaseActivity()))
        mBinding.srlLayout.setRefreshFooter(BallPulseFooter(getBaseActivity()))

        mBinding.rlList.layoutManager = LayoutManagerUtils.verticalLinearLayoutManager(getBaseActivity())
        mBinding.rlList.addItemDecoration(LayoutManagerUtils.borderDivider(0, ResTool.getDimens(R.dimen.d_4), 0, 0))
        mBinding.rlList.adapter = adapter

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

        adapter.collectClickListener { view, position, item ->
            item?.let {
                if (AccountUtil.isNoLogin()) {
                    ActivityUtil.startLoginActivity(getBaseActivity())
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

    private fun loadMore() {
        mViewModel.getProjectArticle(pager, cid)
    }

    private fun refresh() {
        pager = 1
        mBinding.srlLayout.autoRefreshAnimationOnly()
        loadMore()
    }

    override fun initData() {
        super.initData()
        projectTree = arguments?.getParcelable(ConstantKey.KEY_OBJECT)
        projectTree?.let {
            cid = it.id
        }

        mViewModel.projectArticle.observe(this, Observer {
            if (it.cid != cid) {
                mBinding.srlLayout.finishRefresh()
                mBinding.srlLayout.finishLoadMore()
                return@Observer
            }
            val datas = it.datas
            if (pager == 1) {
                mBinding.srlLayout.finishRefresh()
                adapter.removeAll()
            } else {
                mBinding.srlLayout.finishLoadMore()
            }

            if (datas.isEmpty()) {
                if (pager == 1) {
                    // 显示空view
                } else {
                    ResTool.getString(R.string.s_5).toast()
                }
                return@Observer
            }
            adapter.addAll(datas.toMutableList())
            pager = it.curPage + 1
        })
    }

    override fun onResume() {
        super.onResume()
        if (isFirst) {
            refresh()
        }
        isFirst = false
    }
}