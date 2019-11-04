package com.lengjiye.code.project

import com.lengjiye.base.fragment.BaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.databinding.FragmentProjectBinding
import com.lengjiye.code.home.viewmodel.HomeViewModel

class ProjectFragment : BaseFragment<FragmentProjectBinding, HomeViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_project
    }

    override fun getViewModel(): HomeViewModel {
        return HomeViewModel(getBaseActivity().application)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): FragmentProjectBinding {
        return mBinding
    }
}