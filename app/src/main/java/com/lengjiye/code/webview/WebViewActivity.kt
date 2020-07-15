package com.lengjiye.code.webview

import android.graphics.Bitmap
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.TextView
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.databinding.ActivityWebviewBinding
import com.lengjiye.code.constant.IntentKey
import com.lengjiye.code.utils.ToolBarUtils
import com.lengjiye.tools.log.logI

class WebViewActivity : BaseActivity<ActivityWebviewBinding, WebViewModel>() {

    private var mAgentWeb: AgentWeb? = null
    private var titleView: TextView? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_webview
    }

    override fun initToolBar() {
        super.initToolBar()
        val toolbar = ToolBarUtils.Builder(findViewById(R.id.toolbar))
            .setType(ToolBarUtils.NORMAL_TYPE)
            .setNormalTitleColor(R.color.c_ff)
            .setBackListener {
                val b = mAgentWeb?.back()
                if (b == false) finish()
            }
            .setCloseListener {
                finish()
            }
            .builder()
        this.setSupportActionBar(toolbar)
        titleView = ToolBarUtils.getNormalTitle(toolbar)
    }

    override fun initData() {
        super.initData()
        val url = intent.extras?.getString(IntentKey.KEY_WEB_URL) ?: return

        val p = System.currentTimeMillis()
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mBinding.linear, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(mWebChromeClient)
            .setWebViewClient(mWebViewClient)
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            //打开其他应用时，弹窗咨询用户是否前往其他应用
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
            //拦截找不到相关页面的Scheme
            .interceptUnkownUrl()
            .createAgentWeb()
            .ready()
            .go(url)


        val n = System.currentTimeMillis()
        logI("Info", "init used time:" + (n - p))
    }

    private val mWebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            //do you  work
            logI("Info", "BaseWebActivity onPageStarted")
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            titleView?.text = title
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (mAgentWeb?.handleKeyEvent(keyCode, event) == true) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

}