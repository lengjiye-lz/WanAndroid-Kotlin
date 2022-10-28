package com.lengjiye.code.project.adapter

import android.content.Context
import android.view.View
import com.lengjiye.code.recycleview.BaseDBAdapter
import com.lengjiye.code.recycleview.BaseDBViewHolder
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ItemProjectItemBinding
import com.lengjiye.code.utils.GlideUtils
import com.lengjiye.room.entity.HomeEntity
import com.lengjiye.tools.ResTool

/**
 * 文章列表适配器
 */
class ProjectFragmentItemAdapter constructor(val context: Context, models: MutableList<HomeEntity>?) :
    BaseDBAdapter<HomeEntity, ProjectFragmentItemAdapter.HomeModelHolderDB, ItemProjectItemBinding>(context, models) {

    private var listener: ((view: View, position: Int, item: HomeEntity?) -> Unit)? = null

    override fun onBindViewHolder(holder: HomeModelHolderDB, position: Int, item: HomeEntity?) {
        item?.let {
            holder.binding.tvTitle.text = ResTool.fromHtml(it.title).trim()
            holder.binding.tvAuthor.text = getAuthor(it)
            holder.binding.tvDesc.text = ResTool.fromHtml(it.desc).trim()
            holder.binding.tvTime.text = it.niceDate
            GlideUtils.loadRoundedCornersImage(context, it.envelopePic, R.dimen.d_2, holder.binding.ivEnvelope)
            GlideUtils.loadImage(context, if (it.collect) R.drawable.collect_icon_pre else R.drawable.collect_icon_nor, holder.binding.ivCollect)
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

    private fun getCategory(HomeEntity: HomeEntity): String {
        return HomeEntity.superChapterName + "/${HomeEntity.chapterName}"
    }

    fun collectClickListener(listener: ((view: View, position: Int, item: HomeEntity?) -> Unit)?) {
        this.listener = listener
    }

    class HomeModelHolderDB(binding: ItemProjectItemBinding) : BaseDBViewHolder<ItemProjectItemBinding>(binding)

    override fun getLayoutId(): Int {
        return R.layout.item_project_item
    }

    override fun getViewHolder(binding: ItemProjectItemBinding): HomeModelHolderDB {
        return HomeModelHolderDB(binding)
    }
}