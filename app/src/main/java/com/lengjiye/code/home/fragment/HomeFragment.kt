package com.lengjiye.code.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lengjiye.base.fragment.LazyBaseFragment
import com.lengjiye.base.recycleview.HeaderAndFooterWrapper
import com.lengjiye.code.R
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.databinding.FragmentHomeBinding
import com.lengjiye.code.home.adapter.HomeFragmentAdapter
import com.lengjiye.code.home.bean.BannerBean
import com.lengjiye.code.home.viewmodel.HomeViewModel
import com.lengjiye.code.utils.*
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
    private var page = 0
    private var banner: Banner? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun bindViewModel() {
        mBinding.viewModel = mViewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.srlLayout.setRefreshHeader(MaterialHeader(getBaseActivity()))
        mBinding.srlLayout.setRefreshFooter(BallPulseFooter(getBaseActivity()))

        mBinding.rlList.layoutManager = LayoutManagerUtils.verticalLinearLayoutManager(getBaseActivity())
        mBinding.rlList.adapter = header
        initBanner()

        mBinding.srlLayout.setOnRefreshListener {
            refresh()
        }

        mBinding.srlLayout.setOnLoadMoreListener {
            mViewModel.getHomeData(this, page)
        }

        adapter.setOnItemClickListener { v, position, item ->
            item?.let {
                ActivityUtil.startWebViewActivity(this.getBaseActivity(), it.link)
            }
        }

        mBinding.rlList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    bannerIsStartPlay(recyclerView)
                }
            }
        })

        adapter.collectClickListener { view, position, item ->
            item?.let {
                if (AccountUtil.isNoLogin()) {
                    ActivityUtil.startLoginActivity(getBaseActivity())
                    return@let
                }

                if (it.collect) {
                    mViewModel.unCollectArticle(this, it.id)
                } else {
                    mViewModel.collectAddArticle(this, it.id)
                }

                it.collect = !it.collect
                adapter.getItems().set(position, it)
                header.notifyItemChanged(position + header.getHeadersCount())
            }
        }
    }


    /**
     * 控制banner开始滚动
     *
     * banner隐藏的时候不滚动
     */
    private fun bannerIsStartPlay(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is LinearLayoutManager) {
            val firstSize = layoutManager.findFirstVisibleItemPosition()
            if (firstSize == 0) {
                banner?.startAutoPlay()
            } else {
                banner?.stopAutoPlay()
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
            page = it.curPage
        })

        mViewModel.homeBeanList.observe(this, Observer {
            mBinding.srlLayout.finishRefresh()
            if (page == 0) {
                adapter.removeAll()
            }
            adapter.addAll(it.toMutableList())
            header.notifyDataSetChanged()
            page = 1
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
        page = 0
        mViewModel.getHomeTopAndFirstListData(this)
        mViewModel.homeBeanTopAndFirstList?.clear()
    }

    private fun initBanner() {
        val view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.home_header_view, null, false)
        banner = view.findViewById(R.id.banner)
        banner?.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
                if (path is BannerBean) {
                    GlideUtil.loadImage(context, path.imagePath, imageView)
                }
            }
        })
            ?.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            ?.setOnBannerListener { position ->
                val value = mViewModel.bannerList.value?.get(position)
                value?.let {
                    ActivityUtil.startWebViewActivity(this.getBaseActivity(), it.url)
                }

            }
        header.addHeaderView(view)
    }
}