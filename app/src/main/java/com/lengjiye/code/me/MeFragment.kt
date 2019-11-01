package com.lengjiye.code.me

import com.lengjiye.base.BaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.databinding.FragmentMeBinding
import com.lengjiye.code.home.viewmodel.HomeViewModel

class MeFragment : BaseFragment<FragmentMeBinding, HomeViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun getViewModel(): HomeViewModel {
        return HomeViewModel(getBaseActivity().application)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): FragmentMeBinding {
        return mBinding
    }
}