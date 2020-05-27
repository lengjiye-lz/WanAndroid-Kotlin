package com.lengjiye.code.project.adapter

import android.os.Bundle
import android.text.Html
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.project.fragment.ProjectFragmentItem
import com.lengjiye.room.entity.ProjectTreeEntity
import com.lengjiye.tools.ResTool

/**
 * viewpager适配器
 *
 * 采用的新的加载方式
 */
class ProjectAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var treeBeans: List<ProjectTreeEntity>? = null
    var fragment: Fragment? = null

    override fun getItem(position: Int): Fragment {
        val tree = treeBeans?.get(position)
        return ProjectFragmentItem.newInstance(Bundle().apply {
            putParcelable(ConstantKey.KEY_OBJECT, tree)
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
        val treeBean = treeBeans?.get(position)
        return ResTool.fromHtml(treeBean?.name)
    }
}