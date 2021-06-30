package com.lengjiye.code.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lengjiye.base.fragment.LazyParentFragment
import com.lengjiye.base.recycleview.HeaderAndFooterWrapper
import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.databinding.FragmentHomeBinding
import com.lengjiye.code.home.adapter.HomeFragmentAdapter
import com.lengjiye.code.home.viewmodel.HomeViewModel
import com.lengjiye.code.utils.*
import com.lengjiye.room.entity.HomeBannerEntity
import com.lengjiye.tools.ResTool
import com.lengjiye.tools.toast
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import kotlinx.coroutines.launch

/**
 * 首页
 */
class HomeFragment : LazyParentFragment<FragmentHomeBinding, HomeViewModel>() {

    private val adapter by lazy { HomeFragmentAdapter(getBaseActivity(), null) }
    private val header by lazy { HeaderAndFooterWrapper(adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>) }
    private var page = 0
    private var banner: Banner? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
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
            loadMoreData()
        }

        adapter.setOnItemClickListener { v, position, item ->
            item?.let {
                ActivityUtils.startWebViewActivity(this.getBaseActivity(), it.link)
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
                if (AccountUtils.isNoLogin()) {
                    ActivityUtils.startLoginActivity(getBaseActivity())
                    return@let
                }

                if (it.collect) {
                    mViewModel.unCollectArticle(it.id)
                } else {
                    mViewModel.collectAddArticle(it.id)
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

    override fun lazyLiveDataListener() {
        super.lazyLiveDataListener()
        mViewModel.articleMoreList.observe(this, Observer {
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

        mViewModel.homeRefreshList.observe(this, Observer {
            if (it.second.isEmpty()) {
                mBinding.srlLayout.finishRefresh()
                return@Observer
            }
            if (it.first) {
                if (adapter.itemCount <= 0) {
                    adapter.removeAll()
                    adapter.addAll(it.second.toMutableList())
                    header.notifyDataSetChanged()
                }
            } else {
                mBinding.srlLayout.finishRefresh()
                adapter.removeAll()
                adapter.addAll(it.second.toMutableList())
                header.notifyDataSetChanged()
            }
            page = 1
        })

        mViewModel.bannerList.observe(this, Observer {
            if (it.second.isEmpty()) {
                return@Observer
            }
            banner?.setImages(it.second.filter { homeBannerEntity -> homeBannerEntity.visible == 1 })
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

    override fun lazyData() {
        mViewModel.getBanner()
        refresh()
    }

    fun refresh() {
        page = 0
        if (getBaseActivity() is BaseActivity) {
            (getBaseActivity() as BaseActivity).goScrollToTopInterfaceAnimation(mBinding.rlList, 0)
        }
        mBinding.srlLayout.autoRefreshAnimationOnly()
        mViewModel.getHomeTopAndFirstListData()
    }

    private fun loadMoreData() {
        mViewModel.getHomeData(page)
    }

    private fun initBanner() {
        val view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.home_header_view, null, false)
        banner = view.findViewById(R.id.banner)
        banner?.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
                if (path is HomeBannerEntity) {
                    GlideUtils.loadImage(context, path.imagePath, imageView)
                }
            }
        })
            ?.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            ?.setOnBannerListener { position ->
                val value = mViewModel.bannerList.value?.second?.filter { it.visible == 1 }?.get(position)
                value?.let {
                    ActivityUtils.startWebViewActivity(this.getBaseActivity(), it.url)
                }
            }
        header.addHeaderView(view)
    }
}