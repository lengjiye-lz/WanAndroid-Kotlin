package com.lengjiye.code.system.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.code.system.fragment.SystemItemFragment
import com.lengjiye.tools.LogTool

class SystemAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var treeBeans: List<TreeBean>? = null

    fun setDatas(treeBeans: List<TreeBean>?) {
        this.treeBeans = treeBeans
    }

    override fun getItem(position: Int): Fragment {
        val secondTrees = treeBeans?.get(position)
        return SystemItemFragment.newInstance(Bundle().apply {
            putParcelable(ConstantKey.KEY_OBJECT, secondTrees)
        })
    }

    override fun getCount(): Int {
        return treeBeans?.size ?: 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val secondTrees = treeBeans?.get(position)
        return secondTrees?.name
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        LogTool.e("destroyItem position:$position")
    }
}