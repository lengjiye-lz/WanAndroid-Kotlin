package com.lengjiye.codelibrarykotlin.home.fragment

import com.lengjiye.base.BaseFragment
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.databinding.FragmentHomeBinding
import com.lengjiye.codelibrarykotlin.home.viewmodel.HomeViewMode

class MeFragment : BaseFragment<FragmentHomeBinding, HomeViewMode>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): HomeViewMode {
        return HomeViewMode(getBaseActivity().application)
    }

    override fun bindViewModel() {
        getBinding()?.viewModel = mViewModel
    }

    private fun getBinding(): FragmentHomeBinding? {
        return mBinding
    }
}