package com.lengjiye.base

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.lengjiye.base.viewmodel.BaseViewModel


abstract class BaseActivity<T : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    lateinit var mBinding: T
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = ViewModelProvider(this).get(getViewModel()::class.java)
        mBinding.lifecycleOwner = this
        bindViewModel()
        mViewModel.onCreate()
        initIntent(savedInstanceState)
        initView(savedInstanceState)
        initToolBar()
        initLiveDataListener()
        initData()
    }

    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): VM

    /**
     * 绑定 ViewModel
     */
    abstract fun bindViewModel()

    /**
     * 初始化 view
     * 设置view监听
     */
    open fun initView(savedInstanceState: Bundle?) = Unit

    /**
     * 初始化数据
     * 设置数据
     * 请求接口等
     */
    open fun initData() = Unit

    /**
     * intent 传值
     */
    open fun initIntent(savedInstanceState: Bundle?) = Unit

    /**
     * LiveData 数据监听
     */
    open fun initLiveDataListener() = Unit

    open fun initToolBar() = Unit

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        Log.e("lz", "isAppIsInBackground(this):${isAppIsInBackground(this)}")
    }

    /**
     * 判断应用是否在后台
     *
     * @param context
     * @return
     */
    private fun isAppIsInBackground(context: Context): Boolean {
        var isInBackground = true
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = am.runningAppProcesses
        for (processInfo in runningProcesses) {
            //前台程序
            if (processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (activeProcess in processInfo.pkgList) {
                    if (activeProcess == context.getPackageName()) {
                        isInBackground = false
                    }
                }
            }
        }
        return isInBackground
    }

}