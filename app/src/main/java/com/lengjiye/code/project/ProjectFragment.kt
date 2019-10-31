package com.lengjiye.code.project

import com.lengjiye.base.BaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.databinding.FragmentHomeTestBinding
import com.lengjiye.code.home.viewmodel.HomeViewMode

class ProjectFragment : BaseFragment<FragmentHomeTestBinding, HomeViewMode>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_test
    }

    override fun getViewModel(): HomeViewMode {
        return HomeViewMode(getBaseActivity().application)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): FragmentHomeTestBinding {
        return mBinding
    }
}