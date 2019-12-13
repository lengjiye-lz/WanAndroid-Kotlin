package com.lengjiye.code.me.adapter

import android.content.Context
import android.text.Html
import android.view.View
import com.lengjiye.base.recycleview.BaseDBAdapter
import com.lengjiye.base.recycleview.BaseDBViewHolder
import com.lengjiye.code.R
import com.lengjiye.code.constant.HomeFragmentAdapterType
import com.lengjiye.code.databinding.ItemCollectArticelListBinding
import com.lengjiye.code.home.bean.HomeBean

/**
 * 文章列表适配器
 */
class CollectArticleListAdapter constructor(context: Context, models: MutableList<HomeBean>?) :
    BaseDBAdapter<HomeBean, CollectArticleListAdapter.CollectArticleHolderDB, ItemCollectArticelListBinding>(context, models) {

    var type: Int = HomeFragmentAdapterType.TYPE_1

    override fun onBindViewHolder(holder: CollectArticleHolderDB, position: Int, item: HomeBean?) {
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


    class CollectArticleHolderDB(binding: ItemCollectArticelListBinding) : BaseDBViewHolder<ItemCollectArticelListBinding>(binding)

    override fun getLayoutId(): Int {
        return R.layout.item_collect_articel_list
    }

    override fun getViewHolder(binding: ItemCollectArticelListBinding): CollectArticleHolderDB {
        return CollectArticleHolderDB(binding)
    }
}