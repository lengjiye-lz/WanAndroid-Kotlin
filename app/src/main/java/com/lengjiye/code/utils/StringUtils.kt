package com.lengjiye.code.utils

import android.content.Context
import android.text.Editable
import android.widget.Toast
import com.lengjiye.base.application.MasterApplication

/**
 * Created by chrisw on 2018/9/19.
 */
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun String.toast(duration: Int = Toast.LENGTH_SHORT) {
    toast(com.lengjiye.base.application.MasterApplication.getInstance().applicationContext())
}

fun String.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this, duration).apply {
        show()
    }
}
