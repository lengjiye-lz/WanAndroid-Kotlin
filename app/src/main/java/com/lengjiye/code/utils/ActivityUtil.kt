package com.lengjiye.code.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lengjiye.base.fragment.BaseFragment
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.constant.LoginActivityType
import com.lengjiye.code.login.activity.LoginActivity
import com.lengjiye.code.me.activity.*
import com.lengjiye.code.webview.WebViewActivity

inline fun <reified T : Activity> Context.startActivity() {
    startActivity<T>(null)
}

inline fun <reified T : Activity> Context.startActivity(bundle: Bundle?) {
    val intent = Intent(this, T::class.java)
    bundle?.let { intent.putExtras(bundle) }
    startActivity(intent)
}

object ActivityUtil {
    fun startWebViewActivity(context: Context, url: String) {
        context.startActivity<WebViewActivity>(Bundle().apply {
            putString(ConstantKey.KEY_WEB_URL, url)
        })
    }

    fun startLoginActivity(context: Context, type: Int = LoginActivityType.TYPE_1) {
        context.startActivity<LoginActivity>(Bundle().apply {
            putInt(ConstantKey.KEY_TYPE, type)
        })
    }

    fun startRankTableActivity(context: Context) {
        context.startActivity<RankTableActivity>()
    }

    fun startCoinListActivity(context: Context) {
        context.startActivity<CoinListActivity>()
    }

    fun startMyCollectActivity(context: Context) {
        context.startActivity<MyCollectActivity>()
    }

    fun startCollectArticleListActivity(context: Context) {
        context.startActivity<CollectArticleListActivity>()
    }

    fun startCollectWebsiteListActivity(context: Context) {
        context.startActivity<CollectWebsiteListActivity>()
    }

    fun startSettingActivity(context: Context) {
        context.startActivity<SettingActivity>()
    }

    fun startMyShareActivity(context: Context) {
        context.startActivity<MyShareActivity>()
    }
}