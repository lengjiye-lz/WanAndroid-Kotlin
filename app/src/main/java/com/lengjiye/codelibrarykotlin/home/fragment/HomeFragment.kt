package com.lengjiye.codelibrarykotlin.home.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lengjiye.base.LazyBaseFragment
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.databinding.FragmentHomeBinding
import com.lengjiye.codelibrarykotlin.home.adapter.HomeFragmentAdapter
import com.lengjiye.codelibrarykotlin.home.viewmodel.HomeViewMode

class HomeFragment : LazyBaseFragment<FragmentHomeBinding, HomeViewMode>() {

    private val adapter by lazy { HomeFragmentAdapter(getBaseActivity(), null) }

    private var pager = 0

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

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        getBinding().rlList.layoutManager = LinearLayoutManager(getBaseActivity())
        getBinding().rlList.adapter = adapter
    }

    override fun initData() {
        super.initData()

        mViewModel.article.observe(this, Observer {
            val dates = it.datas
            if (dates.isEmpty()) {
                return@Observer
            }
            adapter.addAll(dates.toMutableList())
            pager++
        })

        mViewModel.homeBeanList.observe(this, Observer {
            if (pager == 0) {
                adapter.removeAll()
            }
            adapter.addAll(it.toMutableList())
            pager = 1
        })

    }

    override fun loadData() {
        refresh()
//        mViewModel.getHomeData(this, pager)
    }

    private fun refresh() {
        pager = 0
        mViewModel.getHomeTopAndFirstListData(this)
    }

}