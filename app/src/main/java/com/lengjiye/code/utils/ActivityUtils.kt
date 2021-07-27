package com.lengjiye.code.utils

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
//import androidx.activity.result.ActivityResult
//import androidx.activity.result.ActivityResultCallback
//import androidx.activity.result.contract.ActivityResultContracts

import com.lengjiye.code.constant.IntentKey
import com.lengjiye.code.constant.LoginActivityType
import com.lengjiye.code.login.activity.LoginActivity
import com.lengjiye.code.me.activity.*
import com.lengjiye.code.search.activity.SearchActivity
import com.lengjiye.code.webview.WebViewActivity


inline fun <reified T : Activity> Context.startActivity() {
    startActivity<T>(null)
}

inline fun <reified T : Activity> Context.startActivity(bundle: Bundle?) {
    val intent = Intent(this, T::class.java)
    bundle?.let { intent.putExtras(bundle) }
    startActivity(intent)
}

/**
 * 新的方式，直接回掉
 *
 * demo:
 * startActivityForResult<LiveReadyActivity>(Bundle().apply {
 *    putString("test", "test")
 * }){// 返回参数
 *   it.data?.extras?.getString("test1")
 *   }
 */
//inline fun <reified T : ComponentActivity> ComponentActivity.startActivityForResult(
//    bundle: Bundle?,
//    callback: ActivityResultCallback<ActivityResult>
//) {
//    val intent = Intent(this, T::class.java)
//    bundle?.let { intent.putExtras(bundle) }
//    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback).launch(intent)
//}

object ActivityUtils {
    fun startWebViewActivity(context: Context, url: String) {
        context.startActivity<WebViewActivity>(Bundle().apply {
            putString(IntentKey.KEY_WEB_URL, url)
        })
    }

    fun startLoginActivity(context: Context, type: Int = LoginActivityType.TYPE_1) {
        context.startActivity<LoginActivity>(Bundle().apply {
            putInt(IntentKey.KEY_TYPE, type)
        })
    }

    fun startRankTableActivity(context: Context) {
        context.startActivity<RankTableActivity>()
    }

    fun startCoinListActivity(context: Context) {
        if (!AccountUtils.isLogin()) {
            startLoginActivity(context)
            return
        }
        context.startActivity<CoinListActivity>()
    }

    fun startMyCollectActivity(context: Context) {
        if (!AccountUtils.isLogin()) {
            startLoginActivity(context)
            return
        }
        context.startActivity<MyCollectActivity>()
    }

    fun startCollectArticleListActivity(context: Context) {
        if (!AccountUtils.isLogin()) {
            startLoginActivity(context)
            return
        }
        context.startActivity<CollectArticleListActivity>()
    }

    fun startCollectWebsiteListActivity(context: Context) {
        if (!AccountUtils.isLogin()) {
            startLoginActivity(context)
            return
        }
        context.startActivity<CollectWebsiteListActivity>()
    }

    fun startSettingActivity(context: Context) {
        context.startActivity<SettingActivity>()
    }

    fun startMyShareActivity(context: Context) {
        if (!AccountUtils.isLogin()) {
            startLoginActivity(context)
            return
        }
        context.startActivity<MyShareActivity>()
    }

    fun startShareArticleListActivity(context: Context, userId: Int) {
        if (!AccountUtils.isLogin()) {
            startLoginActivity(context)
            return
        }
        context.startActivity<ShareArticleListActivity>(Bundle().apply {
            putInt(IntentKey.KEY_USER_ID, userId)
        })
    }

    fun startSearchActivity(context: Context) {
        context.startActivity<SearchActivity>()
    }
}