package com.lengjiye.code.me.adapter

import android.content.Context
import com.lengjiye.code.recycleview.BaseDBAdapter
import com.lengjiye.code.recycleview.BaseDBViewHolder
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ItemCoinListBinding
import com.lengjiye.code.me.bean.Coin

/**
 * 积分明细
 */
class CoinListAdapter constructor(context: Context, models: MutableList<Coin>?) :
    BaseDBAdapter<Coin, CoinListAdapter.CoinHolderDB, ItemCoinListBinding>(context, models) {

    override fun getLayoutId(): Int {
        return R.layout.item_coin_list
    }

    override fun onBindViewHolder(holder: CoinHolderDB, position: Int, item: Coin?) {
        item?.let {
            holder.binding.tvTitle.text = it.desc
        }
    }

    override fun getViewHolder(binding: ItemCoinListBinding): CoinHolderDB {
        return CoinHolderDB(binding)
    }

    class CoinHolderDB(binding: ItemCoinListBinding) : BaseDBViewHolder<ItemCoinListBinding>(binding)
}