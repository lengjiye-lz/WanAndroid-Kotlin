package com.lengjiye.code.home.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.AbsListView
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.lengjiye.base.fragment.LazyBaseFragment
import com.lengjiye.base.recycleview.HeaderAndFooterWrapper
import com.lengjiye.code.R
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.databinding.FragmentHomeBinding
import com.lengjiye.code.home.adapter.HomeFragmentAdapter
import com.lengjiye.code.home.bean.BannerBean
import com.lengjiye.code.home.viewmodel.HomeViewModel
import com.lengjiye.code.utils.ActivityUtil
import com.lengjiye.code.utils.GlideUtil
import com.lengjiye.code.utils.toast
import com.lengjiye.code.widgets.SpaceDecoration
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
        mBinding.viewModel = mViewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.srlLayout.setRefreshHeader(MaterialHeader(getBaseActivity()))
        mBinding.srlLayout.setRefreshFooter(BallPulseFooter(getBaseActivity()))

        mBinding.rlList.layoutManager = LinearLayoutManager(getBaseActivity())
        mBinding.rlList.adapter = header
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
            LogTool.e("lz", "firstSize:$firstSize")
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
            pager = it.curPage
        })

        mViewModel.homeBeanList.observe(this, Observer {
            mBinding.srlLayout.finishRefresh()
            if (pager == 0) {
                adapter.removeAll()
            }
            adapter.addAll(it.toMutableList())
            header.notifyDataSetChanged()
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
                    GlideUtil.loadImage(context, path.imagePath, imageView)
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