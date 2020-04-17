package com.lengjiye.code.search.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lengjiye.base.activity.BaseActivity
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivitySearchBinding
import com.lengjiye.code.home.adapter.HomeFragmentAdapter
import com.lengjiye.code.search.viewmodel.SearchViewModel
import com.lengjiye.code.utils.ActivityUtil
import com.lengjiye.code.utils.ToolBarUtil
import com.lengjiye.utils.RxUtil
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader
import io.reactivex.disposables.Disposable

/**
 * @Author: lz
 * @Date: 2020-03-03
 * @Description:
 */
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {

    private val searchAdapter by lazy { HomeFragmentAdapter(this, null) }

    private var disposableSearch: Disposable? = null
    private var page = 0
    private var key = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun getViewModel(): SearchViewModel {
        return SearchViewModel(application)
    }

    override fun bindViewModel() {
        mBinding.viewModel = mViewModel
    }

    override fun initToolBar() {
        super.initToolBar()
        ToolBarUtil.Builder(findViewById(R.id.toolbar))
            .setType(ToolBarUtil.EXIT_TYPE)
            .setBackListener {
                finish()
            }
            .builder()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val etSearch = ToolBarUtil.getSearchExit(findViewById(R.id.toolbar))
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                key = etSearch.getText()
                if (key.isEmpty()) {
                    loadHistoryData()
                } else {
                    delaySearch()
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
            mViewModel.search(this, page, key)
        }

        searchAdapter.setOnItemClickListener { v, position, item ->
            item?.let {
                ActivityUtil.startWebViewActivity(this, it.link)
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
                if (it.datas.isEmpty()) {
                    // show no data
                } else {
                    mBinding.srlLayout.setEnableLoadMore(true)
                    searchAdapter.replaceAll(it.datas.toMutableList())
                    page++
                }
            } else {
                mBinding.srlLayout.finishLoadMore()
                if (it.datas.isNotEmpty()) {
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
        disposableSearch?.dispose()
        // 延迟请求数据
        disposableSearch = RxUtil.timer(this, 2) {
            refresh()
        }
    }

    private fun refresh() {
        page = 0
        mViewModel.search(this, page, key)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableSearch?.dispose()
    }
}
