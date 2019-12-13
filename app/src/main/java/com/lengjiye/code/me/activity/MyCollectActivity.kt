package com.lengjiye.code.me.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lengjiye.base.BaseActivity
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityCoinListBinding
import com.lengjiye.code.databinding.ActivityMyCollectBinding
import com.lengjiye.code.databinding.ActivityRankTableBinding
import com.lengjiye.code.me.adapter.CoinListAdapter
import com.lengjiye.code.me.adapter.RankTableAdapter
import com.lengjiye.code.me.viewmodel.MeViewModel
import com.lengjiye.code.utils.ActivityUtil
import com.lengjiye.code.utils.toast
import com.lengjiye.tools.ResTool
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader

/**
 * 我的收藏
 */
class MyCollectActivity : BaseActivity<ActivityMyCollectBinding, MeViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_my_collect
    }

    override fun bindViewModel() {
        mBinding.viewModel = mViewModel
    }

    override fun getViewModel(): MeViewModel {
        return MeViewModel(application)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        mBinding.hnArticleList.setOnClickListener {
            ActivityUtil.startCollectArticleListActivity(this)
        }

        mBinding.hnWebsiteList.setOnClickListener {
            ActivityUtil.startCollectWebsiteListActivity(this)
        }
    }
}