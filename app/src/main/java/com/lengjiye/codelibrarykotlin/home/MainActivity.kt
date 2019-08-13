package com.lengjiye.codelibrarykotlin.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.lengjiye.base.BaseActivity
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.databinding.ActivityMainBinding
import com.lengjiye.codelibrarykotlin.home.fragment.HomeFragment
import com.lengjiye.codelibrarykotlin.home.viewmodel.MainViewMode

/**
 * mainActivity
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewMode>() {

    private lateinit var mTempFragment: Fragment

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainViewMode {
        return MainViewMode(application)
    }

    override fun bindViewModel() {
        getBinding()?.viewModel = mViewModel
    }

    /**
     * 获取 mBinding
     */
    private fun getBinding(): ActivityMainBinding? {
        return mBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initBottomNavigation()
    }

    override fun initData() {
        super.initData()
//        switchFragment(0)
        mTempFragment = MainFragmentManager.instance.getHomeFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.f_container, mTempFragment as HomeFragment).commit()
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
        getBinding()?.bnBar?.setBarBackgroundColor(R.color.color_1)
            ?.addItem(
                BottomNavigationItem(R.mipmap.ic_launcher, R.string.s_1)
                    .setInactiveIconResource(R.mipmap.ic_launcher)
                    .setActiveColor(R.color.color_2).setInActiveColor(R.color.color_3)
            )
            ?.addItem(
                BottomNavigationItem(R.mipmap.ic_launcher, R.string.s_2)
                    .setInactiveIconResource(R.mipmap.ic_launcher)
                    .setActiveColor(R.color.color_2).setInActiveColor(R.color.color_3)
            )
            ?.addItem(
                BottomNavigationItem(R.mipmap.ic_launcher, R.string.s_3)
                    .setInactiveIconResource(R.mipmap.ic_launcher)
                    .setActiveColor(R.color.color_2).setInActiveColor(R.color.color_3)
            )
            ?.addItem(
                BottomNavigationItem(R.mipmap.ic_launcher, R.string.s_4)
                    .setInactiveIconResource(R.mipmap.ic_launcher)
                    .setActiveColor(R.color.color_2).setInActiveColor(R.color.color_3)
            )
        getBinding()?.bnBar?.initialise()

        getBinding()?.bnBar?.setMode(BottomNavigationBar.MODE_FIXED)

        getBinding()?.bnBar?.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                Log.e("lz", "onTabSelected:$position")
//                switchFragment(position)
            }
        })
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

}
