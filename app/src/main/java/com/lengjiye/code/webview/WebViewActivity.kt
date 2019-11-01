package com.lengjiye.code.webview

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.lengjiye.base.BaseActivity
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityWebviewBinding
import com.lengjiye.code.constant.ConstantKey

class WebViewActivity : BaseActivity<ActivityWebviewBinding, WebViewModel>() {

    private var mAgentWeb: AgentWeb? = null

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
        mBinding.toolbar.setTitleTextColor(Color.WHITE)
        mBinding.toolbar.title = ""
        this.setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initData() {
        super.initData()
        mBinding.toolbar.setNavigationOnClickListener { finish() }
        val url = intent.extras?.getString(ConstantKey.KEY_WEB_URL) ?: return

        val p = System.currentTimeMillis()
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mBinding.linear, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(mWebChromeClient)
            .setWebViewClient(mWebViewClient)
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .go(url)


        val n = System.currentTimeMillis()
        Log.i("Info", "init used time:" + (n - p))
    }

    private val mWebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted")
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            mBinding.toolbarTitle.text = title
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb?.getWebLifeCycle()?.onDestroy()
    }

}