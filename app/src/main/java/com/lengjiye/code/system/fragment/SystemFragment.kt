package com.lengjiye.code.system.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.lengjiye.base.LazyBaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.databinding.FragmentSystemBinding
import com.lengjiye.code.system.adapter.SystemAdapter
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.code.system.viewmodel.SystemViewModel

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
//        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)

        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val position = p0?.position
                position?.let {
                    mBinding.viewPager.currentItem = it
                }
            }
        })
    }

    override fun initData() {
        super.initData()
        mViewModel.tree.observe(this, Observer {
            initTitle(it)
            adapter.setDatas(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun initTitle(treeBeans: List<TreeBean>) {
        var tab: TabLayout.Tab
        treeBeans.forEachIndexed { index, tree ->
            tab = mBinding.tabLayout.newTab()
            tab.tag = index
            tab.text = tree.name
            mBinding.tabLayout.addTab(tab)
        }
    }

}