package com.lengjiye.code.home.adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.lengjiye.base.recycleview.BaseDBAdapter
import com.lengjiye.base.recycleview.BaseDBViewHolder
import com.lengjiye.code.R
import com.lengjiye.code.constant.HomeFragmentAdapterType
import com.lengjiye.code.databinding.ItemHomeBinding
import com.lengjiye.code.home.bean.HomeBean
import com.lengjiye.tools.ResTool

/**
 * 文章列表适配器
 */
class HomeFragmentAdapter constructor(context: Context, models: MutableList<HomeBean>?) :
    BaseDBAdapter<HomeBean, HomeFragmentAdapter.HomeModelHolderDB, ItemHomeBinding>(context, models) {

    var type: Int = HomeFragmentAdapterType.TYPE_1
    private var listener: ((view: View, position: Int, item: HomeBean?) -> Unit)? = null

    override fun onBindViewHolder(holder: HomeModelHolderDB, position: Int, item: HomeBean?) {
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

    private fun getAuthor(homeBean: HomeBean): String {
        if (homeBean.author.isEmpty()) {
            return homeBean.shareUser
        }
        return homeBean.author
    }

    /**
     * 类别
     */
    private fun getCategory(view: TextView, homeBean: HomeBean) {
        val value = if (homeBean.superChapterName.isNullOrEmpty()) {
            if (homeBean.chapterName.isNullOrEmpty()) {
                view.visibility = View.GONE
                ""
            } else {
                homeBean.chapterName
            }
        } else {
            if (homeBean.chapterName.isNullOrEmpty()) {
                homeBean.superChapterName
            } else {
                homeBean.superChapterName + "/${homeBean.chapterName}"
            }
        }
        view.setText(value)
    }

    fun collectClickListener(listener: ((view: View, position: Int, item: HomeBean?) -> Unit)?) {
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