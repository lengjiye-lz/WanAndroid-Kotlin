package com.lengjiye.code.me.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.lengjiye.base.fragment.LazyParentFragment
import com.lengjiye.code.R
import com.lengjiye.code.databinding.FragmentMeBinding
import com.lengjiye.code.me.viewmodel.MeViewModel
import com.lengjiye.code.utils.AccountUtils
import com.lengjiye.code.utils.ActivityUtils
import com.lengjiye.tools.toast
import com.lengjiye.tools.ResTool

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeFragment : LazyParentFragment<FragmentMeBinding, MeViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun isNeedReload(): Boolean {
        return true
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        mBinding.hnRankTable.setOnClickListener {
            ActivityUtils.startRankTableActivity(getBaseActivity())
        }

        mBinding.hnMeCollect.setOnClickListener {
            ActivityUtils.startMyCollectActivity(getBaseActivity())
        }

        mBinding.hnMeShare.setOnClickListener {
            ActivityUtils.startMyShareActivity(getBaseActivity())
        }

        mBinding.tvMeRank.setOnClickListener {
            ActivityUtils.startCoinListActivity(getBaseActivity())
        }

        mBinding.hnMeSetting.setOnClickListener {
            if (!AccountUtils.isLogin()) {
                "没有账号登录".toast()
                return@setOnClickListener
            }
            ActivityUtils.startSettingActivity(getBaseActivity())
        }
    }

    override fun lazyLiveDataListener() {
        super.lazyLiveDataListener()
        mViewModel.rank.observe(this, Observer {
            mBinding.tvMeRank.visibility = View.VISIBLE
            mBinding.tvMeRank.text = ResTool.getStringFormat(R.string.s_14, it.coinCount, it.rank)
        })
    }

    /**
     * 请求接口
     */
    override fun lazyData() {
        if (!AccountUtils.isLogin()) {
            mBinding.tvMeRank.visibility = View.GONE
            mBinding.tvMeName.visibility = View.GONE
            return
        }

        mBinding.tvMeName.visibility = View.VISIBLE
        mBinding.tvMeName.text = AccountUtils.getUserName()
        mViewModel.getCoinUserInfo()
    }
}