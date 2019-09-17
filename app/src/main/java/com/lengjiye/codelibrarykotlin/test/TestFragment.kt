package com.lengjiye.codelibrarykotlin.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.test.adapter.RecycleAdapter
import com.lengjiye.tools.LogTool

class TestFragment : Fragment() {
    private lateinit var s: String
    private lateinit var recycle: RecyclerView
    private lateinit var text: TextView
    private var strings = ArrayList<String>()
    private val adapter by lazy { context?.let { RecycleAdapter(it, strings) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            s = it.getString("String").toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycle = view.findViewById(R.id.recycle)

        recycle.layoutManager = LinearLayoutManager(context)

        recycle.adapter = adapter
        text = view.findViewById(R.id.text)
        text.text = s

        test()
    }

    private fun test() {
        for (i in 0..20) {
            strings.add("测试测试测试测试:$i")
        }
        adapter?.notifyDataSetChanged()
    }
}