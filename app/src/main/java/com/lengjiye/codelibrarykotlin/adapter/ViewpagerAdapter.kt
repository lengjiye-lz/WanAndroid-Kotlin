package com.lengjiye.codelibrarykotlin.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lengjiye.codelibrarykotlin.TestFragment
import kotlin.collections.ArrayList

class ViewpagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private lateinit var fragmentList: ArrayList<Fragment>
    private var dataList = ArrayList<String>()

    fun setData(dataList: ArrayList<String>) {
        this.dataList.addAll(dataList)
        fragmentList = ArrayList(dataList.size)
    }

    override fun getItem(position: Int): Fragment {
        return switchFragment(position)
    }

    /**
     * 选择 fragment
     */
    private fun switchFragment(position: Int): Fragment {
        val size: Int = fragmentList.size
        return if (size > position) {
            fragmentList[position]
        } else {
            var fragment = getFragment(position)
            fragmentList.add(fragment)
            fragment
        }
    }

    /**
     * 获取 fragment
     */
    private fun getFragment(position: Int): Fragment {
        return TestFragment().apply {
            arguments = Bundle().apply {
                putString("String", "$position")
            }
        }
    }

    override fun getCount(): Int {
        return if (dataList.isEmpty()) 0 else dataList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

    }

}