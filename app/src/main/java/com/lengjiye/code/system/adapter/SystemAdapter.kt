package com.lengjiye.code.system.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.system.fragment.SystemFragmentItem
import com.lengjiye.room.entity.SystemTreeEntity
import com.lengjiye.tools.ResTool

class SystemAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var treeBeans: List<SystemTreeEntity>? = null
    var fragment: Fragment? = null

    override fun getItem(position: Int): Fragment {
        val trees = treeBeans?.get(position)
        return SystemFragmentItem.newInstance(Bundle().apply {
            putParcelable(ConstantKey.KEY_OBJECT, trees)
            putInt(ConstantKey.KEY_ID, position)
        })
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        fragment = `object` as Fragment
        super.setPrimaryItem(container, position, `object`)
    }

    override fun getCount(): Int {
        return treeBeans?.size ?: 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val systemTreeEntity = treeBeans?.get(position)
        return ResTool.fromHtml(systemTreeEntity?.name)
    }
}