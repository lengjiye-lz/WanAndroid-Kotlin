package com.lengjiye.code.me.adapter

import android.content.Context
import com.lengjiye.code.recycleview.BaseDBAdapter
import com.lengjiye.code.recycleview.BaseDBViewHolder
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ItemRankTableBinding
import com.lengjiye.code.me.bean.Rank
import com.lengjiye.tools.ResTool

/**
 * 文章列表适配器
 */
class RankTableAdapter constructor(context: Context, models: MutableList<Rank>?) :
    BaseDBAdapter<Rank, RankTableAdapter.RankHolderDB, ItemRankTableBinding>(context, models) {

    override fun getLayoutId(): Int {
        return R.layout.item_rank_table
    }

    override fun onBindViewHolder(holder: RankHolderDB, position: Int, item: Rank?) {
        item?.let {
            holder.binding.tvName.text = it.username
            holder.binding.tvCoin.text = ResTool.getStringFormat(R.string.s_19, it.coinCount)
            holder.binding.tvRank.text = ResTool.getStringFormat(R.string.s_18, it.level)
        }
    }

    override fun getViewHolder(binding: ItemRankTableBinding): RankHolderDB {
        return RankHolderDB(binding)
    }

    class RankHolderDB(binding: ItemRankTableBinding) : BaseDBViewHolder<ItemRankTableBinding>(binding)
}