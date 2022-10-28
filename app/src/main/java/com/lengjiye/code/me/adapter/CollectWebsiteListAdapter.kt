package com.lengjiye.code.me.adapter

import android.content.Context
import android.view.View
import com.lengjiye.code.recycleview.BaseDBAdapter
import com.lengjiye.code.recycleview.BaseDBViewHolder
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ItemCollectWebsiteListBinding
import com.lengjiye.code.me.bean.Website

/**
 * 文章列表适配器
 */
class CollectWebsiteListAdapter constructor(context: Context, models: MutableList<Website>?) :
    BaseDBAdapter<Website, CollectWebsiteListAdapter.CollectArticleHolderDB, ItemCollectWebsiteListBinding>(context, models) {

    private var listener: ((view: View, position: Int, item: Website?) -> Unit)? = null

    override fun onBindViewHolder(holder: CollectArticleHolderDB, position: Int, item: Website?) {
        item?.let {
            holder.binding.tvTitle.text = item.name
            holder.binding.tvTitle.visibility = if (item.visible != 1) View.GONE else View.VISIBLE

            holder.binding.ivCollect.setOnClickListener { view ->
                listener?.invoke(view, position, item)
            }
        }
    }

    fun collectClickListener(listener: ((view: View, position: Int, item: Website?) -> Unit)?) {
        this.listener = listener
    }

    class CollectArticleHolderDB(binding: ItemCollectWebsiteListBinding) : BaseDBViewHolder<ItemCollectWebsiteListBinding>(binding)

    override fun getLayoutId(): Int {
        return R.layout.item_collect_website_list
    }

    override fun getViewHolder(binding: ItemCollectWebsiteListBinding): CollectArticleHolderDB {
        return CollectArticleHolderDB(binding)
    }

}