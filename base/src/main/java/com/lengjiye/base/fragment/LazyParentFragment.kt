package com.lengjiye.base.fragment

import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import com.lengjiye.base.viewmodel.BaseViewModel


/**
 * 懒加载基类
 *
 * 适用于非viewpager
 */
abstract class LazyParentFragment<T : ViewDataBinding, VM : BaseViewModel> : ParentFragment<T, VM>() {
    // 是否已经加载过数据
    private var isDataLoaded = false

    // 记录当前fragment的是否隐藏 配合show()、hide()使用
    private var isHiddenToUser = true

    private var isCreated = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        load()
    }

    /**
     * 需要在懒加载时候处理的view
     */
    open fun lazyView() = Unit

    /**
     * 需要在懒加载时候处理的LiveData
     */
    open fun lazyLiveDataListener() = Unit

    /**
     * 请求加在数据
     *
     * 懒加载处理数据请求
     */
    abstract fun lazyData()

    /**
     * fragment再次可见时，是否重新请求数据，默认为false 只请求一次数据
     *
     * 需要重新请求  重写此方法  返回true
     */
    protected open fun isNeedReload(): Boolean {
        return false
    }

    private fun load() {
        // 防止第一次add时候不加载数据
        isHiddenToUser = false
        isCreated = true
        startLoadData()
    }

    /**
     * fragment 切换调用
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isHiddenToUser = hidden
        Log.e("LazyBaseFragment", "onHiddenChanged:$hidden")
        if (!hidden)
            startLoadData()
    }

    override fun onResume() {
        super.onResume()
        // isNeedReload为true  防止从后台进入前台不请求数据
        Log.e("LazyBaseFragment", "onResume:$isHiddenToUser")
        if (!isHiddenToUser && !isCreated)
            startLoadData()

        isCreated = false

    }

    /**
     * 开始请求数据
     */
    private fun startLoadData() {
        Log.d("LazyBaseFragment", "!isParentHidden():${!isParentHidden()}")
        Log.d("LazyBaseFragment", "isNeedReload():${isNeedReload()}")
        Log.d("LazyBaseFragment", "(isNeedReload() || !isDataLoaded):${(isNeedReload() || !isDataLoaded)}")
        Log.d("LazyBaseFragment", "!isHiddenToUser:${!isHiddenToUser}")
        if (!isParentHidden() && (isNeedReload() || !isDataLoaded) && !isHiddenToUser) {
            Log.d("LazyBaseFragment", "loadData")
            lazyView()
            lazyLiveDataListener()
            lazyData()
            isDataLoaded = true
            dispatchParentHiddenState()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
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
        } else if (fragment is LazyParentFragment<*, *> && !fragment.isHiddenToUser) {
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
            if (it is LazyParentFragment<*, *> && !it.isHidden) {
                Log.d("LazyBaseFragment", "child startLoadData")
                it.startLoadData()
            }
        }
    }

    override fun onDestroy() {
        isDataLoaded = false
        isHiddenToUser = true
        super.onDestroy()
    }
}