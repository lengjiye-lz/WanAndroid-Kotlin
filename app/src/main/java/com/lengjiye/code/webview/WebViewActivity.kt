package com.lengjiye.code.webview

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.TextView
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.lengjiye.base.BaseActivity
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityWebviewBinding
import com.lengjiye.code.constant.ConstantKey
import com.lengjiye.code.utils.ToolBarUtil
import com.lengjiye.tools.LogTool

class WebViewActivity : BaseActivity<ActivityWebviewBinding, WebViewModel>() {

    private var mAgentWeb: AgentWeb? = null
    private var titleView: TextView? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_webview
    }

    override fun getViewModel(): WebViewModel {
        return WebViewModel(application)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): ActivityWebviewBinding {
        return mBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val toolbar = ToolBarUtil.Builder(findViewById(R.id.toolbar))
            .setType(ToolBarUtil.NORMAL_TYPE)
            .setNormalTitleColor(Color.WHITE)
            .setBackListener {
                val b = mAgentWeb?.back()
                if (b == false) finish()
            }
            .setCloseListener {
                finish()
            }
            .builder()
        this.setSupportActionBar(toolbar)
        titleView = ToolBarUtil.getNormalTitle(toolbar)
    }

    override fun initData() {
        super.initData()
        val url = intent.extras?.getString(ConstantKey.KEY_WEB_URL) ?: return

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
        LogTool.i("Info", "init used time:" + (n - p))
    }

    private val mWebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            //do you  work
            LogTool.i("Info", "BaseWebActivity onPageStarted")
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
        mAgentWeb?.getWebLifeCycle()?.onPause()
        super.onPause()

    }

    override fun onResume() {
        mAgentWeb?.getWebLifeCycle()?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb?.getWebLifeCycle()?.onDestroy()
    }

}