package com.lengjiye.code.main.manager

import androidx.fragment.app.Fragment
import com.lengjiye.code.home.fragment.HomeFragment
import com.lengjiye.code.me.MeFragment
import com.lengjiye.code.project.ProjectFragment
import com.lengjiye.code.system.SystemFragment
import java.lang.ref.WeakReference

class MainFragmentManager private constructor() {
    companion object {
        var instance = Singleton.singleton
    }

    private object Singleton {
        val singleton = MainFragmentManager()
    }

    private var map: MutableMap<String, WeakReference<Fragment>>? = null

    private fun getFragmentMap(): MutableMap<String, WeakReference<Fragment>> {
        if (map == null) {
            map = hashMapOf()
        }
        return map as MutableMap<String, WeakReference<Fragment>>
    }

    fun getHomeFragment(): HomeFragment {
        if (getFragmentMap()[HomeFragment::class.java.simpleName] == null) {
            val fragment = HomeFragment()
            getFragmentMap().put(HomeFragment::class.java.simpleName, WeakReference(fragment))
        }
        return getFragmentMap()[HomeFragment::class.java.simpleName]?.get() as HomeFragment
    }

    fun getSystemFragment(): SystemFragment {
        if (getFragmentMap()[SystemFragment::class.java.simpleName] == null) {
            val fragment = SystemFragment()
            getFragmentMap().put(SystemFragment::class.java.simpleName, WeakReference(fragment))
        }
        return getFragmentMap()[SystemFragment::class.java.simpleName]?.get() as SystemFragment
    }

    fun getProjectFragment(): ProjectFragment {
        if (getFragmentMap()[ProjectFragment::class.java.simpleName] == null) {
            val fragment = ProjectFragment()
            getFragmentMap().put(ProjectFragment::class.java.simpleName, WeakReference(fragment))
        }
        return getFragmentMap()[ProjectFragment::class.java.simpleName]?.get() as ProjectFragment
    }

    fun getMeFragment(): MeFragment {
        if (getFragmentMap()[MeFragment::class.java.simpleName] == null) {
            val fragment = MeFragment()
            getFragmentMap().put(MeFragment::class.java.simpleName, WeakReference(fragment))
        }
        return getFragmentMap()[MeFragment::class.java.simpleName]?.get() as MeFragment
    }

    fun destroy() {
        map?.clear()
    }

    fun destroy(fragment: Fragment?) {
        if (map == null || fragment == null) {
            return
        }
        getFragmentMap().remove(fragment.javaClass.simpleName)
    }
}