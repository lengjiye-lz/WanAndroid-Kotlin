package com.lengjiye.code.search.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.databinding.ActivitySearchBinding
import com.lengjiye.code.home.adapter.HomeFragmentAdapter
import com.lengjiye.code.search.viewmodel.SearchViewModel
import com.lengjiye.code.utils.ActivityUtils
import com.lengjiye.code.utils.ToolBarUtils
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch

/**
 * @Author: lz
 * @Date: 2020-03-03
 * @Description:
 */
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {

    private val searchAdapter by lazy { HomeFragmentAdapter(this, null) }

    private var page = 0
    private var key = ""
    private val searchState = MutableStateFlow("")

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initToolBar() {
        super.initToolBar()
        ToolBarUtils.Builder(findViewById(R.id.toolbar))
            .setType(ToolBarUtils.EXIT_TYPE)
            .setBackListener {
                finish()
            }
            .builder()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        delaySearch()
        val etSearch = ToolBarUtils.getSearchExit(findViewById(R.id.toolbar))
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                key = etSearch.getText()
                if (key.isEmpty()) {
                    loadHistoryData()
                } else {
                    searchState.value = key
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })



        mBinding.srlLayout.setRefreshHeader(MaterialHeader(this))
        mBinding.srlLayout.setRefreshFooter(BallPulseFooter(this))

        mBinding.srlLayout.setEnableLoadMore(false)
        mBinding.rlSearchHistory.layoutManager = LinearLayoutManager(this)
        mBinding.rlSearchHistory.adapter = searchAdapter

        mBinding.srlLayout.setOnRefreshListener {
            refresh()
        }

        mBinding.srlLayout.setOnLoadMoreListener {
            mViewModel.search(page, key)
        }

        searchAdapter.setOnItemClickListener { v, position, item ->
            item?.let {
                ActivityUtils.startWebViewActivity(this, it.link)
            }
        }


    }

    override fun initData() {
        super.initData()
        loadHistoryData()
    }

    override fun initLiveDataListener() {
        super.initLiveDataListener()
        mViewModel.searchBean.observe(this, Observer {
            if (page == 0) {
                mBinding.srlLayout.finishRefreshWithNoMoreData()
                if (it?.datas?.isEmpty() == true) {
                    // show no data
                } else {
                    mBinding.srlLayout.setEnableLoadMore(true)
                    searchAdapter.replaceAll(it?.datas?.toMutableList())
                    page++
                }
            } else {
                mBinding.srlLayout.finishLoadMore()
                if (it?.datas?.isNotEmpty() == true) {
                    searchAdapter.addAll(it.datas.toMutableList())
                    page++
                } else {
                    mBinding.srlLayout.setEnableLoadMore(false)
                }
            }
        })
    }

    /**
     * 加载历史记录
     */
    private fun loadHistoryData() {

    }

    /**
     * 延迟搜索
     */
    private fun delaySearch() {
        lifecycleScope.launch {
            searchState.sample(500)
                .filter {
                    it.isNotBlank()
                }.collect {
                    refresh()
                }
        }

    }

    private fun refresh() {
        page = 0
        mViewModel.search(page, key)
    }
}
