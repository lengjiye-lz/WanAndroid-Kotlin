package com.lengjiye.code.project.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.lengjiye.base.fragment.BaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.databinding.FragmentProjectBinding
import com.lengjiye.code.project.adapter.ProjectAdapter
import com.lengjiye.code.project.viewmodel.ProjectViewModel
import com.lengjiye.tools.ResTool

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description: 项目
 */
class ProjectFragment : BaseFragment<FragmentProjectBinding, ProjectViewModel>() {

    private val adapter by lazy { ProjectAdapter(childFragmentManager) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_project
    }

    override fun getViewModel(): ProjectViewModel {
        return ProjectViewModel(CodeApplication.instance)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): FragmentProjectBinding {
        return mBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.viewPager.adapter = adapter
        mBinding.viewPager.offscreenPageLimit = 1
        mBinding.viewPager.currentItem = 0
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
        setDivider()
    }

    override fun initData() {
        super.initData()
        mViewModel.projectTree.observe(this, Observer {
            if (it.isEmpty()) {
                return@Observer
            }
            adapter.setDatas(it)
            adapter.notifyDataSetChanged()
        })

        mViewModel.getProjectTree(this)
    }

    @SuppressLint("ResourceType")
    private fun setDivider() {
        val linearLayout = mBinding.tabLayout.getChildAt(0) as LinearLayout
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE)
        linearLayout.setDividerDrawable(Drawable.createFromXml(resources, resources.getXml(R.drawable.tag_linearlayout_vertical_divider)))
        linearLayout.setDividerPadding(ResTool.getDimens(R.dimen.d_16))
    }
}