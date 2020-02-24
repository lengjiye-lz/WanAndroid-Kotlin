package com.lengjiye.code.project.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.lengjiye.base.recycleview.BaseDBAdapter
import com.lengjiye.base.recycleview.BaseDBViewHolder
import com.lengjiye.code.R
import com.lengjiye.code.constant.HomeFragmentAdapterType
import com.lengjiye.code.databinding.ItemHomeBinding
import com.lengjiye.code.databinding.ItemProjectItemBinding
import com.lengjiye.code.home.bean.HomeBean
import com.lengjiye.code.utils.GlideUtil

/**
 * 文章列表适配器
 */
class ProjectFragmentItemAdapter constructor(val context: Context, models: MutableList<HomeBean>?) :
    BaseDBAdapter<HomeBean, ProjectFragmentItemAdapter.HomeModelHolderDB, ItemProjectItemBinding>(context, models) {

    override fun onBindViewHolder(holder: HomeModelHolderDB, position: Int, item: HomeBean?) {
        item?.let {
            holder.binding.tvTitle.text = Html.fromHtml(it.title).trim()
            holder.binding.tvAuthor.text = getAuthor(it)
            holder.binding.tvDesc.text = Html.fromHtml(it.desc).trim()
            holder.binding.tvTime.text = it.niceDate
            GlideUtil.loadRoundedCornersImage(context, it.envelopePic, R.dimen.d_10, holder.binding.ivEnvelope)
//            Glide.with(context).load(it.envelopePic).into(holder.binding.ivEnvelope)
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

    class HomeModelHolderDB(binding: ItemProjectItemBinding) : BaseDBViewHolder<ItemProjectItemBinding>(binding)

    override fun getLayoutId(): Int {
        return R.layout.item_project_item
    }

    override fun getViewHolder(binding: ItemProjectItemBinding): HomeModelHolderDB {
        return HomeModelHolderDB(binding)
    }
}