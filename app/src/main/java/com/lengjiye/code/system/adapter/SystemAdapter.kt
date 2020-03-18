package com.lengjiye.code.system.adapter

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.system.bean.TreeBean
import com.lengjiye.code.system.fragment.SystemFragmentItem
import com.lengjiye.tools.ResTool

class SystemAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var treeBeans: List<TreeBean>? = null

    fun setData(treeBeans: List<TreeBean>?) {
        this.treeBeans = treeBeans
    }

    override fun getItem(position: Int): Fragment {
        val trees = treeBeans?.get(position)
        return SystemFragmentItem.newInstance(Bundle().apply {
            putParcelable(ConstantKey.KEY_OBJECT, trees)
        })
    }

    override fun getCount(): Int {
        return treeBeans?.size ?: 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val treeBean = treeBeans?.get(position)
        return ResTool.fromHtml(treeBean?.name)
    }
}