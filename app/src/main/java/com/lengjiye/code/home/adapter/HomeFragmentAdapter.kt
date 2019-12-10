package com.lengjiye.code.home.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lengjiye.base.recycleview.BaseDBAdapter
import com.lengjiye.base.recycleview.BaseDBViewHolder
import com.lengjiye.code.R
import com.lengjiye.code.constant.HomeFragmentAdapterType
import com.lengjiye.code.databinding.ItemHomeBinding
import com.lengjiye.code.home.bean.HomeBean

/**
 * 文章列表适配器
 */
class HomeFragmentAdapter constructor(context: Context, models: MutableList<HomeBean>?) :
    BaseDBAdapter<HomeBean, HomeFragmentAdapter.HomeModelHolderDB, ItemHomeBinding>(context, models) {

    var type: Int = HomeFragmentAdapterType.TYPE_1

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeModelHolderDB {
//        val binding = DataBindingUtil.inflate<ItemHomeBinding>(
//            mLayoutInflater, R.layout.item_home, parent, false
//        )
//        return HomeModelHolderDB(binding)
//    }

    override fun onBindViewHolder(holder: HomeModelHolderDB, position: Int, item: HomeBean?) {
        item?.let {
            holder.binding.tvTitle.text = Html.fromHtml(it.title).trim()
            holder.binding.tvAuthor.text = getAuthor(it)
            holder.binding.tvCategory.text = getCategory(it)
            holder.binding.tvCategory.visibility = if (type == HomeFragmentAdapterType.TYPE_2) View.GONE else View.VISIBLE
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


    class HomeModelHolderDB(binding: ItemHomeBinding) : BaseDBViewHolder<ItemHomeBinding>(binding)

    override fun getLayoutId(): Int {
        return R.layout.item_home
    }

    override fun getViewHolder(binding: ItemHomeBinding): HomeModelHolderDB {
        return HomeModelHolderDB(binding)
    }
}