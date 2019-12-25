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
import com.lengjiye.code.utils.ToolBarUtil
import com.lengjiye.tools.LogTool
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


/**
 * mainActivity
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var mTempFragment: Fragment
    private var publishSubject: PublishSubject<String> = PublishSubject.create()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainViewModel {
        return MainViewModel(application)
    }

    override fun bindViewModel() {
        mBinding.viewModel = mViewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initBottomNavigation()
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

        test()
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
        mBinding.bnBar.let {
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
                    publishSubject.onNext("asdc")


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


    private fun test() {

        publishSubject
            .debounce(400, TimeUnit.MILLISECONDS)
            .switchMap {
                test1(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { x ->
                    LogTool.e("lz", "x:$x")
                }, {
                    LogTool.e("lz", "error:${it.message}")
                }
            )
    }

    private fun test1(i: String?): Observable<String?>? {
        return Observable.create<String> {
            LogTool.e("lz", "i:$i")
            // 模拟请求
            Thread.sleep(1000)

            if (it != null) {
                i?.let { it1 -> it.onNext(it1) }
                it.onComplete()
            }
        }
    }
}

private class SearchSource(var s: Long) : ObservableSource<String> {
    override fun subscribe(observer: Observer<in String>) {
        LogTool.e("lz", "SearchSource:$s")
        Observable.create<String> {
            // 模拟请求
            Thread.sleep(1000)
            it.onNext("sdcasd")
            it.onComplete()
        }.subscribeOn(Schedulers.io())
            .subscribe({
                LogTool.e("lz", "observer:$observer")
                observer.onNext("sdcasdobserver")
                observer.onComplete()
            }, {
                LogTool.e("lz", "error:${it.message}")
            })
    }
}

