package com.lengjiye.code.system.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.LinearLayout
import androidx.constraintlayout.motion.widget.DesignTool
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.airbnb.lottie.animation.content.Content
import com.google.android.material.tabs.TabLayout
import com.lengjiye.base.LazyBaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.databinding.FragmentSystemBinding
import com.lengjiye.code.system.adapter.SystemAdapter
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.code.system.viewmodel.SystemViewModel
import com.lengjiye.tools.ResTool

class SystemFragment : LazyBaseFragment<FragmentSystemBinding, SystemViewModel>() {

    private val adapter by lazy { SystemAdapter(childFragmentManager) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_system
    }

    override fun getViewModel(): SystemViewModel {
        return SystemViewModel(getBaseActivity().application)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): FragmentSystemBinding {
        return mBinding
    }

    override fun loadData() {
        mViewModel.getTree(this)
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
        mViewModel.tree.observe(this, Observer {
            adapter.setDatas(it)
            adapter.notifyDataSetChanged()
        })
    }


    @SuppressLint("ResourceType")
    private fun setDivider() {
        val linearLayout = mBinding.tabLayout.getChildAt(0) as LinearLayout
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE)
        linearLayout.setDividerDrawable(Drawable.createFromXml(resources, resources.getXml(R.drawable.tag_linearlayout_vertical_divider)))
        linearLayout.setDividerPadding(ResTool.getDimens(R.dimen.d_16))
    }
}