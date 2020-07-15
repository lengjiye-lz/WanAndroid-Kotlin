package com.lengjiye.code.system.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.lengjiye.base.fragment.LazyParentFragment
import com.lengjiye.code.R
import com.lengjiye.code.databinding.FragmentSystemBinding
import com.lengjiye.code.system.adapter.SystemAdapter
import com.lengjiye.code.system.model.SystemModel
import com.lengjiye.code.system.viewmodel.SystemViewModel
import com.lengjiye.tools.ResTool
import com.lengjiye.tools.log.logD

/**
 * 体系
 */
class SystemFragment : LazyParentFragment<FragmentSystemBinding, SystemViewModel>() {

    private val adapter by lazy { SystemAdapter(childFragmentManager) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_system
    }

    override fun lazyData() {
        mViewModel.getTree()
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
        mViewModel.tree.observe(this, Observer {
            if (adapter.count <= 0) {
                adapter.treeBeans = it
                adapter.notifyDataSetChanged()
            } else {
                if (adapter.treeBeans?.equals(it) == false) {
                    adapter.treeBeans = it
                    adapter.notifyDataSetChanged()
                    // 更新数据库
                    SystemModel.singleton.installTree2Room(it)
                    mBinding.tabLayout.post {
                        mBinding.tabLayout.getTabAt(0)?.select()
                    }
                } else {
                    logD("数据相同, 不更新")
                }
            }
        })
    }

    fun refresh() {
        if (adapter.fragment is SystemFragmentItem) {
            (adapter.fragment as SystemFragmentItem).refresh()
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