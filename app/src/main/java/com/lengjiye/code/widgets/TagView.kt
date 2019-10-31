package com.lengjiye.code.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.lengjiye.code.R
import com.lengjiye.code.home.bean.TagBean
import com.lengjiye.tools.ResTool

class TagView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : LinearLayout(context, attrs, defStyleAttr) {
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null, 0)

    init {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        orientation = HORIZONTAL
    }

    fun setTag(type: Int, time: Long, tagBeanList: List<TagBean>?) {
        removeAllViews()
        add(type, time, tagBeanList)
    }

    private fun add(type: Int, time: Long, tagBeanList: List<TagBean>?) {
        var view: View
        // 置顶
        if (type == 1) {
            view = itemView("置顶", R.color.c_E24333, R.drawable.bg_rectangle_white_stroke_e24333)
            addView(view)
        }
        // 新
        val nowTime = System.currentTimeMillis() - 24 * 60 * 60 * 1000
        if (time >= nowTime) {
            view = itemView("新", R.color.c_FF2B33, R.drawable.bg_rectangle_white_stroke_ff2b33)
            addView(view)
        }

        // 公众号
        tagBeanList?.let {
            it.forEach { tag ->
                view = itemView(tag.name, R.color.c_009A61, R.drawable.bg_rectangle_white_stroke_009a61)
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
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResTool.getDimens(R.dimen.d_12).toFloat())
        textView.setTextColor(ResTool.getColor(color))
        textView.setBackgroundResource(bg)
        textView.setPadding(ResTool.getDimens(R.dimen.d_5), ResTool.getDimens(R.dimen.d_1), ResTool.getDimens(R.dimen.d_5), ResTool.getDimens(R.dimen.d_3))
        return textView
    }


}