package com.lengjiye.code.search.fragment

import com.lengjiye.base.fragment.LazyBaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.databinding.FragmentSearchBinding
import com.lengjiye.code.search.viewmodel.SearchViewModel

/**
 * @Author: lz
 * @Date: 2020-03-03
 * @Description:
 */
class SearchFragment : LazyBaseFragment<FragmentSearchBinding, SearchViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }

    override fun loadData() {

    }
}