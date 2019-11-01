package com.lengjiye.code.system.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.code.system.fragment.SystemItemFragment

class SystemAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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
}