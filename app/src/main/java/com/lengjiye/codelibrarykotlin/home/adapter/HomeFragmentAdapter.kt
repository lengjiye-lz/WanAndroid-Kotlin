package com.lengjiye.codelibrarykotlin.home.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lengjiye.base.recycleview.BaseDBAdapter
import com.lengjiye.base.recycleview.BaseDBViewHolder
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.databinding.ItemHomeBinding
import com.lengjiye.codelibrarykotlin.home.bean.HomeBean

class HomeFragmentAdapter constructor(context: Context, models: ArrayList<HomeBean>?) :
    BaseDBAdapter<HomeBean, HomeFragmentAdapter.HomeModelHolderDB>(context, models) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeModelHolderDB {
        val binding = DataBindingUtil.inflate<ItemHomeBinding>(
            mLayoutInflater, R.layout.item_home, parent, false
        )
        return HomeModelHolderDB(binding)
    }

    override fun onBindViewHolder(holder: HomeModelHolderDB, position: Int, item: HomeBean?) {
        item?.let {
            holder.binding.tvTitle.text = Html.fromHtml(it.title).trim()
            holder.binding.tvAuthor.text = getAuthor(it)
            holder.binding.tvCategory.text = getCategory(it)
            holder.binding.tvTime.text = it.niceDate
            holder.binding.tgList.setTag(it.type, it.publishTime, it.tagBeans)
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


    class HomeModelHolderDB(binding: ItemHomeBinding) : BaseDBViewHolder<ItemHomeBinding>(binding)
}