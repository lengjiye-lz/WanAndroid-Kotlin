package com.lengjiye.code.widgets

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.lengjiye.code.R
import com.lengjiye.tools.ResTool
import kotlinx.android.synthetic.main.widget_delete_edittext.view.*


/**
 * 带删除带edit
 */
class DeleteEditTextView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : RelativeLayout(context, attrs, defStyleAttr) {
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null, 0)

    init {
        LayoutInflater.from(context).inflate(R.layout.widget_delete_edittext, this)

        val array = context?.obtainStyledAttributes(attrs, R.styleable.DeleteEditTextView)
        val icon = array?.getDrawable(R.styleable.DeleteEditTextView_icon)
        val text = array?.getString(R.styleable.DeleteEditTextView_text)
        val size = array?.getDimensionPixelSize(R.styleable.DeleteEditTextView_textSize, resources.getDimensionPixelSize(R.dimen.d_18))
        val hint = array?.getResourceId(R.styleable.DeleteEditTextView_hint, R.string.s_7)
        val inputType = array?.getInt(R.styleable.DeleteEditTextView_inputType, 0)
        val lineVisibility = array?.getBoolean(R.styleable.DeleteEditTextView_lineVisibility, true)

        array?.recycle()

        setIcon(icon)
        setText(text)
        setTextSize(size)
        setHint(hint)
        setListener()
        setSingleLine()
        setInputType(inputType)
        setLineVisibility(lineVisibility)
    }

    private fun setIcon(icon: Drawable?) {
        icon?.let {
            iv_icon.setImageDrawable(it)
        }
    }

    private fun setText(text: String?) {
        text?.let {
            et_text.setText(it)
        }
    }

    private fun setTextSize(size: Int?) {
        size?.let {
            et_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.toFloat())
        }
    }

    private fun setHint(hint: Int?) {
        hint?.let {
            et_text.setHint(it)
        }
    }

    private fun setSingleLine() {
        et_text.setSingleLine()
    }

    private fun setInputType(type: Int?) {
        if (type == 2) {
            et_text.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            et_text.inputType = InputType.TYPE_CLASS_TEXT
        }
    }

    private fun setLineVisibility(type: Boolean?) {
        if (type == false) {
            line.visibility = View.GONE
        } else {
            line.visibility = View.VISIBLE
        }
    }

    private fun setListener() {
        et_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    iv_delete.visibility = if (it.isEmpty()) View.INVISIBLE else View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        et_text.setOnFocusChangeListener { view, b ->
            if (b) {
                line.setBackgroundColor(ResTool.getColor(R.color.c_e24333))
            } else {
                line.setBackgroundColor(ResTool.getColor(R.color.c_4697fa))
            }
        }

        iv_delete.setOnClickListener {
            et_text.setText("")
        }
    }

    fun getText(): String {
        return et_text.text.toString().trim()
    }

    fun setEditText(value: String) {
        et_text.setText(value)
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        et_text.addTextChangedListener(watcher)
    }
}