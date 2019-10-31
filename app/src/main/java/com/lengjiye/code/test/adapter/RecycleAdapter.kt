package com.lengjiye.code.test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.lengjiye.code.R
import kotlinx.android.synthetic.main.item_test.view.*

open class RecycleAdapter(var mContext: Context, private val strings: ArrayList<String>?) :
    Adapter<RecycleAdapter.TestHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false)
        return TestHolder(view)
    }

    override fun getItemCount(): Int = if (strings == null || strings.isEmpty()) 0 else strings.size

    override fun onBindViewHolder(holder: TestHolder, position: Int) {
        var s = strings?.get(position)
        if (s != null) {
            holder.bind(s)
        }
    }


    class TestHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(s: String) {
            view.text.text = s
        }
    }
}