package com.lengjiye.codelibrarykotlin.home.fragment

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import com.lengjiye.base.BaseFragment
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.databinding.FragmentHomeBinding
import com.lengjiye.codelibrarykotlin.home.HomeBean
import com.lengjiye.codelibrarykotlin.home.adapter.HomeFragmentAdapter
import com.lengjiye.codelibrarykotlin.home.viewmodel.HomeViewMode

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewMode>() {

    private val adapter by lazy { HomeFragmentAdapter(getBaseActivity(), null) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): HomeViewMode {
        return HomeViewMode(getBaseActivity().application)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): FragmentHomeBinding {
        return mBinding
    }

    @SuppressLint("CheckResult", "AutoDispose")
    override fun initData() {
        super.initData()
        getBinding().rlList.layoutManager = LinearLayoutManager(getBaseActivity())
        getBinding().rlList.adapter = adapter

        test()
    }

    private fun test() {
        val list: MutableList<HomeBean> = ArrayList()
        var homeBean: HomeBean
        listOf("1", "2", "3", "4", "5", "6", "7", "8").forEach {
            homeBean = HomeBean()
            list.add(homeBean)
        }
        adapter.addAll(list)
    }
}