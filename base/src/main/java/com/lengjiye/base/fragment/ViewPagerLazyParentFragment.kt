package com.lengjiye.base.fragment

import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import com.lengjiye.base.viewmodel.BaseViewModel


/**
 * 懒加载基类
 * viewpager 懒加载
 */
abstract class ViewPagerLazyParentFragment<T : ViewDataBinding, VM : BaseViewModel> : ParentFragment<T, VM>() {
    // view是否创建成功  配合viewpager使用
    private var isViewCreated = false
    // fragment是否可见  配合viewpager使用
    private var isVisibleToUser = false
    // 是否已经加载过数据
    private var isDataLoaded = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewCreated = true
        // 防止第一次add时候不加载数据
        startLoadData()
    }

    /**
     * 配合viewpager 切换使用
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        Log.d("LazyBaseFragment", "viewpager isVisibleToUser:$isVisibleToUser")
        startLoadData()
    }

    /**
     * 请求加在数据
     */
    abstract fun loadData()

    /**
     * 开始请求数据
     *
     * 配合viewpager使用
     */
    fun startLoadData() {
        Log.d("LazyBaseFragment", "viewpager startLoadData")
        Log.d(
            "LazyBaseFragment", "isViewCreated:$isViewCreated, isVisibleToUser:$isVisibleToUser, isParentVisible():${isParentVisible()}, " +
                    "isNeedReload():${isNeedReload()} , !isDataLoaded:${!isDataLoaded}"
        )
        if (isViewCreated && isVisibleToUser && isParentVisible() && (isNeedReload() || !isDataLoaded)) {
            Log.d("LazyBaseFragment", "viewpager loadData")
            loadData()
            isDataLoaded = true
            // 通知子fragment请求数据
            dispatchParentVisibleState()
        }
    }

    /**
     * 判断父fragment是否可见
     *
     * 配合viewpager使用
     */
    private fun isParentVisible(): Boolean {
        val fragment = parentFragment
        return fragment == null || (fragment as ParentFragment<*, *>).userVisibleHint
    }

    /**
     * 当前fragment可见，如果其子fragment也可见，则尝试让子fragment请求数据
     *
     * 配合viewpager使用
     */
    private fun dispatchParentVisibleState() {
        val fragmentManager = childFragmentManager
        val fragmentList = fragmentManager.fragments
        if (fragmentList.isEmpty()) return
        fragmentList.forEach {
            if (it is ViewPagerLazyParentFragment<*, *> && it.isVisibleToUser) {
                Log.d("LazyBaseFragment", "viewpager child startLoadData")
                it.startLoadData()
            }
        }
    }

    /**
     * fragment再次可见时，是否重新请求数据，默认为false 只请求一次数据
     *
     * 需要重新请求  重写此方法  返回true
     */
    protected open fun isNeedReload(): Boolean {
        return false
    }

    override fun onDestroy() {
        isViewCreated = false
        isVisibleToUser = false
        isDataLoaded = false
        super.onDestroy()
    }
}