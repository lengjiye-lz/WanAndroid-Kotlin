package com.lengjiye.base

import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import com.lengjiye.base.viewmodel.BaseViewModel


/**
 * 懒加载基类
 */
abstract class LazyBaseFragment<T : ViewDataBinding, VM : BaseViewModel> : BaseFragment<T, VM>() {
    // view是否创建成功  配合viewpager使用
    private var isViewCreated = false
    // fragment是否可见  配合viewpager使用
    private var isVisibleToUser = false
    // 是否已经加载过数据
    private var isDataLoaded = false
    // 记录当前fragment的是否隐藏 配合show()、hide()使用
    private var isHiddenToUser = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewCreated = true
        // 第一次add时候不加载数据
        startLoadData1()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
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
        return fragment == null || (fragment is LazyBaseFragment<*, *> && fragment.isVisibleToUser)
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
            if (it is LazyBaseFragment<*, *> && it.isVisibleToUser) {
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

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isHiddenToUser = hidden
        Log.e("LazyBaseFragment", "onHiddenChanged:$hidden")
        if (!hidden)
            startLoadData1()
    }

    /**
     * 开始请求数据
     */
    private fun startLoadData1() {
        Log.d("LazyBaseFragment", "startLoadData")
        if (!isParentHidden() && (isNeedReload() || !isDataLoaded)) {
            Log.d("LazyBaseFragment", "loadData")
            loadData()
            isDataLoaded = true
            dispatchParentHiddenState()
        }
    }

    /**
     * 父fragment是否隐藏
     *
     * 配合show()、hide()使用
     */
    private fun isParentHidden(): Boolean {
        val fragment = parentFragment
        if (fragment == null) {
            return false
        } else if (fragment is LazyBaseFragment<*, *> && fragment.isHiddenToUser) {
            return false
        }
        return true
    }

    /**
     * 加载子fragment数据
     *
     * 配合show()、hide()使用
     */
    private fun dispatchParentHiddenState() {
        val fragmentManager = childFragmentManager
        val fragmentList = fragmentManager.fragments
        if (fragmentList.isEmpty()) return
        fragmentList.forEach {
            if (it is LazyBaseFragment<*, *> && !it.isHidden) {
                Log.d("LazyBaseFragment", "child startLoadData")
                it.startLoadData1()
            }
        }
    }
}