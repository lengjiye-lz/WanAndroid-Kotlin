package com.lengjiye.code.search.fragment

import com.lengjiye.base.fragment.LazyParentFragment
import com.lengjiye.code.R
import com.lengjiye.code.databinding.FragmentSearchBinding
import com.lengjiye.code.search.viewmodel.SearchViewModel

/**
 * @Author: lz
 * @Date: 2020-03-03
 * @Description:
 */
class SearchFragment : LazyParentFragment<FragmentSearchBinding, SearchViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }

    override fun loadData() {

    }
}