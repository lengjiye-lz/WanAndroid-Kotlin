package com.lengjiye.code.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lengjiye.base.fragment.BaseFragment
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.webview.WebViewActivity

inline fun <reified T : Activity> Context.startActivity() {
    startActivity<T>(null)
}

inline fun <reified T : Activity> Context.startActivity(bundle: Bundle?) {
    val intent = Intent(this, T::class.java)
    bundle?.let { intent.putExtras(bundle) }
    startActivity(intent)
}

class ActivityUtil {
    companion object {
        fun startWebViewActivity(context: Context, url: String) {
            context.startActivity<WebViewActivity>(Bundle().apply {
                putString(ConstantKey.KEY_WEB_URL, url)
            })
        }
    }
}