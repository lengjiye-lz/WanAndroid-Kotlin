package com.lengjiye.code.main.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.lengjiye.base.activity.ParentActivity
import com.lengjiye.code.R
import com.lengjiye.code.application.CodeApplication
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.databinding.ActivityMainBinding
import com.lengjiye.code.home.bean.HotKey
import com.lengjiye.code.home.fragment.HomeFragment
import com.lengjiye.code.main.manager.MainFragmentManager
import com.lengjiye.code.main.viewmodel.MainViewModel
import com.lengjiye.code.utils.ActivityLifecycleCallback
import com.lengjiye.code.utils.ActivityUtil
import com.lengjiye.code.utils.ToolBarUtil
import com.lengjiye.tools.log.LogServiceInstance
import com.lengjiye.utils.RxUtil
import io.reactivex.disposables.Disposable

/**
 * MainActivity
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var mTempFragment: Fragment

    private var disposable: Disposable? = null
    private var hotKeys: List<HotKey>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        // 注册activity生命周期
        CodeApplication.instance.registerActivityLifecycleCallbacks(ActivityLifecycleCallback())
        initBottomNavigation()
        ToolBarUtil.getSearchTitle(findViewById(R.id.toolbar)).setOnClickListener {
            ActivityUtil.startSearchActivity(this)
        }
    }

    override fun initToolBar() {
        super.initToolBar()
        setSupportActionBar(
            ToolBarUtil.Builder(findViewById(R.id.toolbar))
                .setType(ToolBarUtil.SEARCH_TYPE)
                .setSearchLogoRes(R.mipmap.logo)
                .setSearchTitle("经常搜索的几个关键词")
                .builder()
        )
    }

    override fun initData() {
        super.initData()
        mTempFragment = MainFragmentManager.instance.getHomeFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.f_container, mTempFragment as HomeFragment)
            .show(mTempFragment as HomeFragment).commit()

        mViewModel.getHotKeyList()
        // 显示悬浮窗
//        LogServiceInstance.singleton.start(this)
    }

    override fun initLiveDataListener() {
        super.initLiveDataListener()
        mViewModel.hotKeyList.observe(this, Observer {
            this.hotKeys = it
            interval()
        })
    }

    override fun onStart() {
        super.onStart()
        if (disposable?.isDisposed == true && !hotKeys.isNullOrEmpty()) {
            interval()
        }
    }

    /**
     * 启动循环
     */
    private fun interval() {
        disposable?.dispose()
        hotKeys?.let {
            val text = ToolBarUtil.getSearchTitle(findViewById(R.id.toolbar))
            if (text.visibility != View.VISIBLE) {
                return
            }
            val size = it.size
            disposable = RxUtil.interval(3) { it1 ->
                val index = (it1 % size).toInt()
                text.setText(hotKeys?.get(index)?.name)
                text.next()
            }
        }
    }

    /**
     * 切换fragment
     */
    private fun switchFragment(fragment: Fragment) {
        if (fragment == mTempFragment) {
            return
        }
        if (fragment.isAdded) {
            supportFragmentManager.beginTransaction().hide(mTempFragment).show(fragment).commit()
        } else {
            supportFragmentManager.beginTransaction().hide(mTempFragment).add(R.id.f_container, fragment).commit()
        }
        mTempFragment = fragment
    }

    /**
     * 初始化底部按钮
     */
    private fun initBottomNavigation() {
        mBinding.bnBar.let {
            it.setBarBackgroundColor(R.color.c_ff)
                .addItem(
                    BottomNavigationItem(R.drawable.ic_home_2cac77_24dp_pre, R.string.s_1)
                        .setInactiveIconResource(R.drawable.ic_home_a4aca9_24dp_nor)
                        .setActiveColorResource(R.color.c_4697fa).setInActiveColorResource(R.color.c_99)
                )
                .addItem(
                    BottomNavigationItem(R.drawable.ic_data_usage_2cac77_24dp_pre, R.string.s_33)
                        .setInactiveIconResource(R.drawable.ic_data_usage_a4aca9_24dp_nor)
                        .setActiveColorResource(R.color.c_4697fa).setInActiveColorResource(R.color.c_99)
                )
                .addItem(
                    BottomNavigationItem(R.drawable.ic_device_hub_2cac77_24dp_pre, R.string.s_2)
                        .setInactiveIconResource(R.drawable.ic_device_hub_a4aca9_24dp_nor)
                        .setActiveColorResource(R.color.c_4697fa).setInActiveColorResource(R.color.c_99)
                )
                .addItem(
                    BottomNavigationItem(R.drawable.ic_mode_edit_2cac77_24dp_pre, R.string.s_3)
                        .setInactiveIconResource(R.drawable.ic_mode_edit_a4aca9_24dp_nor)
                        .setActiveColorResource(R.color.c_4697fa).setInActiveColorResource(R.color.c_99)
                )
                .addItem(
                    BottomNavigationItem(R.drawable.ic_person_2cac77_24dp_pre, R.string.s_4)
                        .setInactiveIconResource(R.drawable.ic_person_a4aca9_24dp_nor)
                        .setActiveColorResource(R.color.c_4697fa).setInActiveColorResource(R.color.c_99)
                )
                .initialise()

            it.setMode(BottomNavigationBar.MODE_FIXED)

            it.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
                override fun onTabReselected(position: Int) {
                }

                override fun onTabUnselected(position: Int) {
                }

                override fun onTabSelected(position: Int) {
                    switchFragment(position)
                }
            })
        }
    }

    private fun switchFragment(position: Int) {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = MainFragmentManager.instance.getHomeFragment()
            }

            1 -> {
                fragment = MainFragmentManager.instance.getShareFragment()
            }

            2 -> {
                fragment = MainFragmentManager.instance.getSystemFragment()
            }

            3 -> {
                fragment = MainFragmentManager.instance.getProjectFragment()
            }

            4 -> {
                fragment = MainFragmentManager.instance.getMeFragment()
            }
        }

        fragment?.let {
            switchFragment(it)
        }
    }


    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        MainFragmentManager.instance.destroy()
        LogServiceInstance.singleton.stop(this)
    }

}
