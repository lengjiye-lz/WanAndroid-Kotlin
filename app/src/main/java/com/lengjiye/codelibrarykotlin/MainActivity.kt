package com.lengjiye.codelibrarykotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lengjiye.codelibrarykotlin.adapter.ViewpagerAdapter
import com.lengjiye.codelibrarykotlin.view.VerticalViewPager

class MainActivity : AppCompatActivity() {

    private lateinit var viewpager: VerticalViewPager

    private val adapter by lazy { ViewpagerAdapter(supportFragmentManager) }
    private lateinit var strings: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager = findViewById(R.id.viewpager)

        viewpager.adapter = adapter

        test()
    }

    private fun test() {
        strings = ArrayList()
        strings.add("1111")
        strings.add("1111")
        strings.add("1111")
        strings.add("1111")

        adapter.setData(strings)
        adapter.notifyDataSetChanged()
    }
}
