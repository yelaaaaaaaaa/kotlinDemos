package com.example.myapplication.ui.main

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_browser.*
import kotlinx.android.synthetic.main.title_layout.*

class BrowserActivity :BaseActivity(){
    companion object {
        const val URL = "url"
    }
    override fun initData() {

    }

    override fun initView() {
        mToolbar.title = "正在加载..."
        initWebView()
    }

    private fun initWebView() {
        webView.run {
            webViewClient = object : WebViewClient() {

                override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
                    super.onPageStarted(p0, p1, p2)
                    progressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(p0: WebView?, p1: String?) {
                    super.onPageFinished(p0, p1)
                    progressBar.visibility = View.GONE
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(p0: WebView?, p1: Int) {
                    super.onProgressChanged(p0, p1)
                    progressBar.progress = p1
                    Log.e("browser",p1.toString())
                }

                override fun onReceivedTitle(p0: WebView?, p1: String?) {
                    super.onReceivedTitle(p0, p1)
                    p1?.let { mToolbar.title = p1 }
                }

            }
        }
    }
    override fun onBackPressed() {
        if (webView.canGoBack())webView.goBack()
        else super.onBackPressed()
    }
    override fun getLayoutResId(): Int {
        return R.layout.activity_browser
    }
}