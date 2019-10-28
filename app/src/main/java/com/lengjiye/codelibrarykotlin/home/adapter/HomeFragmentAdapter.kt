package com.lengjiye.codelibrarykotlin.home.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lengjiye.base.recycleview.BaseAdapter
import com.lengjiye.base.recycleview.BaseViewHolder
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.databinding.ItemHomeBinding
import com.lengjiye.codelibrarykotlin.home.bean.HomeBean
import com.lengjiye.tools.LogTool

class HomeFragmentAdapter constructor(context: Context, models: MutableList<HomeBean>?) :
    BaseAdapter<HomeBean, HomeFragmentAdapter.HomeModelHolder>(context, models) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeModelHolder {
        val binding = DataBindingUtil.inflate<ItemHomeBinding>(
            mLayoutInflater, R.layout.item_home, parent, false
        )
        return HomeModelHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeModelHolder, position: Int, item: HomeBean?) {
        item?.let {
            holder.binding.tvText.text = it.title
            holder.binding.tvAuthor.text = getAuthor(it)
            holder.binding.tvCategory.text = getCategory(it)
            holder.binding.tvTime.text = it.niceDate
            holder.binding.tgList.setTag(it.type, it.publishTime, it.tags)
            holder.binding.tgList.visibility = if (holder.binding.tgList.childCount == 0) View.GONE else View.VISIBLE
        }
    }

    private fun getAuthor(homeBean: HomeBean): String {
        if (homeBean.author.isEmpty()) {
            return homeBean.shareUser
        }
        return homeBean.author
    }

    private fun getCategory(homeBean: HomeBean): String {
        return homeBean.superChapterName + "/${homeBean.chapterName}"
    }


    class HomeModelHolder(binding: ItemHomeBinding) : BaseViewHolder<ItemHomeBinding>(binding)
}