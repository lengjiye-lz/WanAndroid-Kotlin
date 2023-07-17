package com.lengjiye.code.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.databinding.ActivityMainBinding
import com.lengjiye.code.home.bean.HotKey
import com.lengjiye.code.home.fragment.HomeFragment
import com.lengjiye.code.main.manager.MainFragmentManager
import com.lengjiye.code.main.viewmodel.MainViewModel
import com.lengjiye.code.project.fragment.ProjectFragment
import com.lengjiye.code.share.fragment.ShareFragment
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.code.system.fragment.SystemFragment
import com.lengjiye.code.test.InjectActivity
import com.lengjiye.code.todo.bean.TestBean
import com.lengjiye.code.todo.bean.TodoData
import com.lengjiye.code.utils.ActivityUtils
import com.lengjiye.code.utils.ToolBarUtils
import com.lengjiye.tools.log.LogServiceInstance
import kotlinx.coroutines.*

/**
 * MainActivity
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var mTempFragment: Fragment

    private var hotKeys: List<HotKey>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initBottomNavigation()
        ToolBarUtils.getSearchTitle(findViewById(R.id.toolbar)).setOnClickListener {
            ActivityUtils.startSearchActivity(this)
        }

        mBinding.tvTest.setOnClickListener {
            val intent = Intent(this, InjectActivity::class.java)
            val arr = arrayOf(TestBean(1, 2), TestBean(2, 2))
            intent.putExtra("test1", "asdasdc")
            intent.putExtra("test2", 1)
            intent.putExtra("test3", true)
            intent.putExtra("test4", 1.111110f)
            intent.putExtra("test5", 2.toDouble())
            intent.putExtra("test6", arr)
            startActivity(intent)
        }

    }

    override fun initToolBar() {
        super.initToolBar()
        setSupportActionBar(
            ToolBarUtils.Builder(findViewById(R.id.toolbar))
                .setType(ToolBarUtils.SEARCH_TYPE)
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
    }

    override fun initLiveDataListener() {
        super.initLiveDataListener()
        mViewModel.hotKeyList.observe(this, Observer {
            this.hotKeys = it
            interval()
        })
        // 显示悬浮窗 不能在后台太久
//        LogServiceInstance.singleton.start(this)
        // service在后台太久会被杀掉，需要重启
//        LiveEventBus.get(BaseEventConstant.IS_BACK_GROUND, Boolean::class.java).observeForever {
//            if (!it && LogServiceInstance.isDestroy) {
//                LogServiceInstance.singleton.start(this)
//            }
//        }
    }
//
//    override fun onStart() {
//        super.onStart()
//        if (!hotKeys.isNullOrEmpty()) {
//            interval()
//        }
//    }

    /**
     * 启动循环
     */
    private fun interval() {
        hotKeys?.let {
            val text = ToolBarUtils.getSearchTitle(findViewById(R.id.toolbar))
            if (text.visibility != View.VISIBLE) {
                return
            }
            text.setText(it[0].name)
            lifecycleScope.launch {
                // TODO:需要重写
                repeat(Int.MAX_VALUE) { _ ->
                    it.forEach {
                        delay(3000)
                        withContext(Dispatchers.Main) {
                            text.setText(it.name)
                            text.next()
                        }
                    }
                }
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
            supportFragmentManager.beginTransaction().hide(mTempFragment)
                .add(R.id.f_container, fragment).commit()
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
                        .setActiveColorResource(R.color.c_4697fa)
                        .setInActiveColorResource(R.color.c_99)
                )
                .addItem(
                    BottomNavigationItem(R.drawable.ic_data_usage_2cac77_24dp_pre, R.string.s_33)
                        .setInactiveIconResource(R.drawable.ic_data_usage_a4aca9_24dp_nor)
                        .setActiveColorResource(R.color.c_4697fa)
                        .setInActiveColorResource(R.color.c_99)
                )
                .addItem(
                    BottomNavigationItem(R.drawable.ic_device_hub_2cac77_24dp_pre, R.string.s_2)
                        .setInactiveIconResource(R.drawable.ic_device_hub_a4aca9_24dp_nor)
                        .setActiveColorResource(R.color.c_4697fa)
                        .setInActiveColorResource(R.color.c_99)
                )
                .addItem(
                    BottomNavigationItem(R.drawable.ic_mode_edit_2cac77_24dp_pre, R.string.s_3)
                        .setInactiveIconResource(R.drawable.ic_mode_edit_a4aca9_24dp_nor)
                        .setActiveColorResource(R.color.c_4697fa)
                        .setInActiveColorResource(R.color.c_99)
                )
                .addItem(
                    BottomNavigationItem(R.drawable.ic_person_2cac77_24dp_pre, R.string.s_4)
                        .setInactiveIconResource(R.drawable.ic_person_a4aca9_24dp_nor)
                        .setActiveColorResource(R.color.c_4697fa)
                        .setInActiveColorResource(R.color.c_99)
                )
                .initialise()

            it.setMode(BottomNavigationBar.MODE_FIXED)

            it.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
                override fun onTabReselected(position: Int) {
                    when (val fragment = getFragment(position)) {
                        is HomeFragment -> {
                            fragment.refresh()
                        }
                        is ShareFragment -> {
                            fragment.lazyData()
                        }
                        is SystemFragment -> {
                            fragment.refresh()
                        }
                        is ProjectFragment -> {
                            fragment.refresh()
                        }
                    }
                }

                override fun onTabUnselected(position: Int) {
                }

                override fun onTabSelected(position: Int) {
                    val fragment = getFragment(position)
                    switchFragment(fragment)
                }
            })
        }
    }

    private fun getFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                MainFragmentManager.instance.getHomeFragment()
            }

            1 -> {
                MainFragmentManager.instance.getShareFragment()
            }

            2 -> {
                MainFragmentManager.instance.getSystemFragment()
            }

            3 -> {
                MainFragmentManager.instance.getProjectFragment()
            }

            4 -> {
                MainFragmentManager.instance.getMeFragment()
            }
            else -> {
                MainFragmentManager.instance.getHomeFragment()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        MainFragmentManager.instance.destroy()
        LogServiceInstance.singleton.stop(this)
    }
}
