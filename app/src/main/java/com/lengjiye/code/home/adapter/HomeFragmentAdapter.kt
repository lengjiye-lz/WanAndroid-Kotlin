package com.lengjiye.code.home.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.lengjiye.code.recycleview.BaseDBAdapter
import com.lengjiye.code.recycleview.BaseDBViewHolder
import com.lengjiye.code.R
import com.lengjiye.code.constant.HomeFragmentAdapterType
import com.lengjiye.code.databinding.ItemHomeBinding
import com.lengjiye.room.entity.HomeEntity
import com.lengjiye.tools.ResTool

/**
 * 文章列表适配器
 */
class HomeFragmentAdapter constructor(context: Context, models: MutableList<HomeEntity>?) :
    BaseDBAdapter<HomeEntity, HomeFragmentAdapter.HomeModelHolderDB, ItemHomeBinding>(context, models) {

    var type: Int = HomeFragmentAdapterType.TYPE_1
    private var listener: ((view: View, position: Int, item: HomeEntity?) -> Unit)? = null

    override fun onBindViewHolder(holder: HomeModelHolderDB, position: Int, item: HomeEntity?) {
        item?.let {
            holder.binding.tvTitle.text = ResTool.fromHtml(it.title).trim()
            holder.binding.tvAuthor.text = getAuthor(it)
            holder.binding.tvCategory.visibility = if (type == HomeFragmentAdapterType.TYPE_2) View.GONE else View.VISIBLE
            getCategory(holder.binding.tvCategory, it)
            holder.binding.tvTime.text = it.niceDate
            holder.binding.ivCollect.isSelected = it.collect
            holder.binding.tgList.setTag(it.type, it.publishTime, it.tags)
            holder.binding.tgList.visibility = if (holder.binding.tgList.childCount == 0) View.GONE else View.VISIBLE

            holder.binding.ivCollect.setOnClickListener { view ->
                listener?.invoke(view, position, item)
            }
        }
    }

    private fun getAuthor(HomeEntity: HomeEntity): String {
        if (HomeEntity.author.isEmpty()) {
            return HomeEntity.shareUser
        }
        return HomeEntity.author
    }

    /**
     * 类别
     */
    private fun getCategory(view: TextView, HomeEntity: HomeEntity) {
        val value = if (HomeEntity.superChapterName.isNullOrEmpty()) {
            if (HomeEntity.chapterName.isNullOrEmpty()) {
                view.visibility = View.GONE
                ""
            } else {
                HomeEntity.chapterName
            }
        } else {
            if (HomeEntity.chapterName.isNullOrEmpty()) {
                HomeEntity.superChapterName
            } else {
                HomeEntity.superChapterName + "/${HomeEntity.chapterName}"
            }
        }
        view.setText(value)
    }

    fun collectClickListener(listener: ((view: View, position: Int, item: HomeEntity?) -> Unit)?) {
        this.listener = listener
    }


    class HomeModelHolderDB(binding: ItemHomeBinding) : BaseDBViewHolder<ItemHomeBinding>(binding)

    override fun getLayoutId(): Int {
        return R.layout.item_home
    }

    override fun getViewHolder(binding: ItemHomeBinding): HomeModelHolderDB {
        return HomeModelHolderDB(binding)
    }
}