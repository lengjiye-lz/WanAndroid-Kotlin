package com.lengjiye.codelibrarykotlin.home.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lengjiye.base.recycleview.BaseAdapter
import com.lengjiye.base.recycleview.BaseViewHolder
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.databinding.ItemHomeBinding
import com.lengjiye.codelibrarykotlin.home.bean.HomeBean

class HomeFragmentAdapter constructor(context: Context, models: MutableList<HomeBean>?) :
    BaseAdapter<HomeBean, HomeFragmentAdapter.HomeModelHolder>(context, models) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeModelHolder {
        val binding = DataBindingUtil.inflate<ItemHomeBinding>(
            mLayoutInflater, R.layout.item_home, parent, false
        )
        return HomeModelHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeModelHolder, position: Int, item: HomeBean?) {
        item?.let {
            holder.binding.tvText.text = "测试:$position"
        }
    }

    class HomeModelHolder(binding: ItemHomeBinding) : BaseViewHolder<ItemHomeBinding>(binding)
}