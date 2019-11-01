package com.lengjiye.code.main.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.lengjiye.base.BaseActivity
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityMainBinding
import com.lengjiye.code.home.fragment.HomeFragment
import com.lengjiye.code.main.manager.MainFragmentManager
import com.lengjiye.code.main.viewmodel.MainViewModel

/**
 * mainActivity
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var mTempFragment: Fragment

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainViewModel {
        return MainViewModel(application)
    }

    override fun bindViewModel() {

        getBinding().viewModel = mViewModel
    }

    /**
     * 获取 mBinding
     */
    override fun getBinding(): ActivityMainBinding {
        return mBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initBottomNavigation()
    }

    override fun initData() {
        super.initData()
        mTempFragment = MainFragmentManager.instance.getHomeFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.f_container, mTempFragment as HomeFragment)
            .show(mTempFragment as HomeFragment).commit()
    }

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
        getBinding().bnBar.let {
            it.setBarBackgroundColor(R.color.c_ff)
                .addItem(
                    BottomNavigationItem(R.mipmap.ic_launcher, R.string.s_1)
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColor(R.color.c_4697fa).setInActiveColor(R.color.c_1a)
                )
                .addItem(
                    BottomNavigationItem(R.mipmap.ic_launcher, R.string.s_2)
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColor(R.color.c_4697fa).setInActiveColor(R.color.c_1a)
                )
                .addItem(
                    BottomNavigationItem(R.mipmap.ic_launcher, R.string.s_3)
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColor(R.color.c_4697fa).setInActiveColor(R.color.c_1a)
                )
                .addItem(
                    BottomNavigationItem(R.mipmap.ic_launcher, R.string.s_4)
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColor(R.color.c_4697fa).setInActiveColor(R.color.c_1a)
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
                fragment = MainFragmentManager.instance.getSystemFragment()
            }

            2 -> {
                fragment = MainFragmentManager.instance.getProjectFragment()
            }

            3 -> {
                fragment = MainFragmentManager.instance.getMeFragment()
            }
        }

        fragment?.let {
            switchFragment(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MainFragmentManager.instance.destroy()
    }

}
