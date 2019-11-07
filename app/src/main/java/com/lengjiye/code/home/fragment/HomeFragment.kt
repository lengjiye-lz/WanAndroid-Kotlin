package com.lengjiye.code.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lengjiye.base.fragment.LazyBaseFragment
import com.lengjiye.base.recycleview.HeaderAndFooterWrapper
import com.lengjiye.code.R
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.databinding.FragmentHomeBinding
import com.lengjiye.code.home.adapter.HomeFragmentAdapter
import com.lengjiye.code.home.bean.BannerBean
import com.lengjiye.code.home.viewmodel.HomeViewModel
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.utils.ActivityUtil
import com.lengjiye.code.utils.startActivity
import com.lengjiye.code.utils.toast
import com.lengjiye.code.webview.WebViewActivity
import com.lengjiye.tools.LogTool
import com.lengjiye.tools.ResTool
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader

/**
 * 首页
 */
class HomeFragment : LazyBaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val adapter by lazy { HomeFragmentAdapter(getBaseActivity(), null) }
    private val header by lazy { HeaderAndFooterWrapper(adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>) }
    private var pager = 0
    private var banner: Banner? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): HomeViewModel {
        return HomeViewModel(CodeApplication.instance)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): FragmentHomeBinding {
        return mBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        getBinding().srlLayout.setRefreshHeader(MaterialHeader(getBaseActivity()))
        getBinding().srlLayout.setRefreshFooter(BallPulseFooter(getBaseActivity()))

        getBinding().rlList.layoutManager = LinearLayoutManager(getBaseActivity())
        getBinding().rlList.adapter = header
        initBanner()

        mBinding.srlLayout.setOnRefreshListener {
            refresh()
        }

        mBinding.srlLayout.setOnLoadMoreListener {
            mViewModel.getHomeData(this, pager)
        }

        adapter.setOnItemClickListener { v, position, item ->
            item?.let {
                ActivityUtil.startWebViewActivity(this.getBaseActivity(), it.link)
            }
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.article.observe(this, Observer {
            mBinding.srlLayout.finishLoadMore()
            val dates = it.datas
            if (dates.isEmpty()) {
                ResTool.getString(R.string.s_5).toast()
                return@Observer
            }
            adapter.addAll(dates.toMutableList())
            header.notifyItemRangeInserted(header.itemCount, dates.size)
            pager = it.curPage
        })

        mViewModel.homeBeanList.observe(this, Observer {
            mBinding.srlLayout.finishRefresh()
            if (pager == 0) {
                adapter.removeAll()
            }
            adapter.addAll(it.toMutableList())
            header.notifyItemRangeInserted(header.itemCount, it.size)
            pager = 1
        })

        mViewModel.bannerList.observe(this, Observer {
            if (it.isEmpty()) {
                return@Observer
            }
            banner?.setImages(it)
            banner?.start()
        })


    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            banner?.stopAutoPlay()
        } else {
            banner?.startAutoPlay()
        }
    }

    override fun loadData() {
        mViewModel.getBanner(this)
        refresh()
    }

    private fun refresh() {
        pager = 0
        mViewModel.getHomeTopAndFirstListData(this)
        mViewModel.homeBeanTopAndFirstList?.clear()
    }

    private fun initBanner() {
        val view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.home_header_view, null, false)
        banner = view.findViewById(R.id.banner)
        banner?.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
                if (path is BannerBean) {
                    Glide.with(context).load(path.imagePath).into(imageView)
                }
            }
        })
            ?.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            ?.setOnBannerListener { position ->
                val value = mViewModel.bannerList.value?.get(position)
                LogTool.e("value:${value?.title}")
                value?.let {
                    ActivityUtil.startWebViewActivity(this.getBaseActivity(), it.url)
                }

            }
        header.addHeaderView(view)
    }
}