package com.lengjiye.code.search.activity

import com.lengjiye.base.BaseActivity
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivitySearchBinding
import com.lengjiye.code.search.viewmodel.SearchViewModel

/**
 * @Author: lz
 * @Date: 2020-03-03
 * @Description:
 */
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun getViewModel(): SearchViewModel {
        return SearchViewModel(application)
    }

    override fun bindViewModel() {
        mBinding.viewModel = mViewModel
    }
}
