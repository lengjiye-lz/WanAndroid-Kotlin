package com.lengjiye.code.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.lengjiye.code.R
import kotlinx.android.synthetic.main.widget_horizontal_navigation.view.*

/**
 * 横向的导航条
 */
class HorizontalNavigation(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)


    init {
        LayoutInflater.from(context).inflate(R.layout.widget_horizontal_navigation, this)
        val array = context.obtainStyledAttributes(attrs, R.styleable.HorizontalNavigation)
        val text = array.getString(R.styleable.HorizontalNavigation_widgets_content)
        array.recycle()

        setContent(text)
    }

    private fun setContent(text: String?) {
        text?.let {
            tv_content.setText(it)
        }
    }
}