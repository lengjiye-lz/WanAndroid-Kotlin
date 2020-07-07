package com.lengjiye.code.project.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.lengjiye.base.fragment.LazyParentFragment
import com.lengjiye.base.fragment.ParentFragment
import com.lengjiye.code.R
import com.lengjiye.code.databinding.FragmentProjectBinding
import com.lengjiye.code.project.adapter.ProjectAdapter
import com.lengjiye.code.project.model.ProjectModel
import com.lengjiye.code.project.viewmodel.ProjectViewModel
import com.lengjiye.code.system.fragment.SystemFragmentItem
import com.lengjiye.code.system.model.SystemModel
import com.lengjiye.tools.ResTool
import com.lengjiye.tools.log.LogTool

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description: 项目
 */
class ProjectFragment : LazyParentFragment<FragmentProjectBinding, ProjectViewModel>() {

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

    override fun lazyLiveDataListener() {
        super.lazyLiveDataListener()
        mViewModel.projectTree.observe(this, Observer {
            if (adapter.count <= 0) {
                adapter.treeBeans = it
                adapter.notifyDataSetChanged()
            } else {
                if (adapter.treeBeans?.equals(it) == false) {
                    adapter.treeBeans = it
                    adapter.notifyDataSetChanged()
                    // 更新数据库
                    ProjectModel.singleton.installTree2Room(it)
                    mBinding.tabLayout.post {
                        mBinding.tabLayout.getTabAt(0)?.select()
                    }
                } else {
                    LogTool.d("数据相同, 不更新")
                }
            }
        })
    }

    override fun lazyData() {
        mViewModel.getProjectTree()
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