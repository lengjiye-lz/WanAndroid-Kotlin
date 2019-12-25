package com.lengjiye.code.me.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.lengjiye.base.fragment.LazyBaseFragment
import com.lengjiye.code.R
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.databinding.FragmentMeBinding
import com.lengjiye.code.me.viewmodel.MeViewModel
import com.lengjiye.code.utils.AccountUtil
import com.lengjiye.code.utils.ActivityUtil
import com.lengjiye.code.utils.toast
import com.lengjiye.tools.ResTool

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class MeFragment : LazyBaseFragment<FragmentMeBinding, MeViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun getViewModel(): MeViewModel {
        return MeViewModel(CodeApplication.instance)
    }

    override fun bindViewModel() {
        mBinding.viewModel = mViewModel
    }

    override fun isNeedReload(): Boolean {
        return true
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        mBinding.hnRankTable.setOnClickListener {
            ActivityUtil.startRankTableActivity(getBaseActivity())
        }

        mBinding.hnMeCollect.setOnClickListener {
            if (!AccountUtil.isLogin()) {
                ActivityUtil.startLoginActivity(getBaseActivity())
                return@setOnClickListener
            }

            ActivityUtil.startMyCollectActivity(getBaseActivity())
        }

        mBinding.hnMeShare.setOnClickListener {
            if (!AccountUtil.isLogin()) {
                ActivityUtil.startLoginActivity(getBaseActivity())
                return@setOnClickListener
            }
        }

        mBinding.tvMeRank.setOnClickListener {
            if (!AccountUtil.isLogin()) {
                ActivityUtil.startLoginActivity(getBaseActivity())
                return@setOnClickListener
            }

            ActivityUtil.startCoinListActivity(getBaseActivity())
        }

        mBinding.hnMeSetting.setOnClickListener {
            if (!AccountUtil.isLogin()) {
                "没有账号登录".toast()
                return@setOnClickListener
            }
            ActivityUtil.startSettingActivity(getBaseActivity())
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.rank.observe(this, Observer {
            mBinding.tvMeRank.visibility = View.VISIBLE
            mBinding.tvMeRank.text = ResTool.getStringFormat(R.string.s_14, it.coinCount, it.rank)
        })
    }

    /**
     * 请求接口
     */
    override fun loadData() {
        if (!AccountUtil.isLogin()) {
            mBinding.tvMeRank.visibility = View.GONE
            mBinding.tvMeName.visibility = View.GONE
            return
        }

        mBinding.tvMeName.visibility = View.VISIBLE
        mBinding.tvMeName.text = AccountUtil.getUserName()
        mViewModel.getCoinUserInfo(this)
    }
}