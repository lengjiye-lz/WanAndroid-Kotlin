package com.lengjiye.codelibrarykotlin.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.home.bean.Tag
import com.lengjiye.tools.ResTool

class TagView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : LinearLayout(context, attrs, defStyleAttr) {
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null, 0)

    private var tagList: List<Tag>? = null

    init {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        orientation = HORIZONTAL
        setPadding(0, 0, ResTool.getDimens(R.dimen.d_5), 0)
    }

    fun setTag(tagList: List<Tag>) {
        this.tagList = tagList
        removeAllViews()
        add()
    }

    private fun add() {
        tagList?.let {
            var view: View
            var tagName: String
            it.forEach { tag ->
                tagName = tag.name
                when (tagName) {
                    "置顶" -> {
                        view = itemView(tagName, R.color.c_E24333, R.drawable.bg_rectangle_white_stroke_e24333)
                    }

                    "新" -> {
                        view = itemView(tagName, R.color.c_FF2B33, R.drawable.bg_rectangle_white_stroke_ff2b33)
                    }

//                    "问答" -> {
//                        view = itemView(tagName, R.color.c_E24333, R.drawable.bg_rectangle_white_stroke_e24333)
//                    }
//
//                    "项目" -> {
//                        view = itemView(tagName, R.color.c_E24333, R.drawable.bg_rectangle_white_stroke_e24333)
//                    }

                    else -> {
                        view = itemView(tagName, R.color.c_009A61, R.drawable.bg_rectangle_white_stroke_009a61)
                    }
                }
                addView(view)
            }
        }
    }

    private fun itemView(text: String, color: Int, bg: Int): View {
        val textView = TextView(context)
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(0, 0, ResTool.getDimens(R.dimen.d_5), 0)
        textView.layoutParams = layoutParams
        textView.text = text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResTool.getDimens(R.dimen.d_14).toFloat())
        textView.setTextColor(ResTool.getColor(color))
        textView.setBackgroundResource(bg)
        textView.setPadding(ResTool.getDimens(R.dimen.d_5), ResTool.getDimens(R.dimen.d_2), ResTool.getDimens(R.dimen.d_5), ResTool.getDimens(R.dimen.d_2))
        return textView
    }


}