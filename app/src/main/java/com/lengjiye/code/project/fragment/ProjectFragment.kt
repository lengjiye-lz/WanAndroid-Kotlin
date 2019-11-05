package com.lengjiye.code.project.fragment

import com.lengjiye.base.fragment.BaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.databinding.FragmentProjectBinding
import com.lengjiye.code.project.viewmodel.ProjectViewModel

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description: 项目
 */
class ProjectFragment : BaseFragment<FragmentProjectBinding, ProjectViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_project
    }

    override fun getViewModel(): ProjectViewModel {
        return ProjectViewModel(CodeApplication.instance)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): FragmentProjectBinding {
        return mBinding
    }
}