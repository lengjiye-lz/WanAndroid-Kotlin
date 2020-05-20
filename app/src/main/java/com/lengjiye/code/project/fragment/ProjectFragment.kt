package com.lengjiye.code.project.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.lengjiye.base.fragment.ParentFragment
import com.lengjiye.code.R
import com.lengjiye.code.databinding.FragmentProjectBinding
import com.lengjiye.code.project.adapter.ProjectAdapter
import com.lengjiye.code.project.viewmodel.ProjectViewModel
import com.lengjiye.code.system.fragment.SystemFragmentItem
import com.lengjiye.tools.ResTool

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description: 项目
 */
class ProjectFragment : ParentFragment<FragmentProjectBinding, ProjectViewModel>() {

    private val adapter by lazy { ProjectAdapter(childFragmentManager) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_project
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
        mViewModel.getProjectTree()
    }

    override fun initLiveDataListener() {
        super.initLiveDataListener()
        mViewModel.projectTree.observe(this, Observer {
            if (it.isEmpty()) {
                return@Observer
            }
            adapter.setDatas(it)
            adapter.notifyDataSetChanged()
        })
    }

    fun refresh() {
        if (adapter.fragment is ProjectFragmentItem) {
            (adapter.fragment as ProjectFragmentItem).refresh()
        }
    }

    @SuppressLint("ResourceType")
    private fun setDivider() {
        val linearLayout = mBinding.tabLayout.getChildAt(0) as LinearLayout
        linearLayout.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        linearLayout.dividerDrawable = Drawable.createFromXml(resources, resources.getXml(R.drawable.tag_linearlayout_vertical_divider))
        linearLayout.dividerPadding = ResTool.getDimens(R.dimen.d_16)
    }
}