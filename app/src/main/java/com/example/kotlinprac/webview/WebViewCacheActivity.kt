package com.example.kotlinprac.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityWebViewCacheBinding

class WebViewCacheActivity :
    BaseActivity<ActivityWebViewCacheBinding>(R.layout.activity_web_view_cache) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.wbTest.apply {
            settings.run {
                javaScriptEnabled = true
                domStorageEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                cacheMode = WebSettings.LOAD_NO_CACHE
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
            }
            loadUrl("https://m.naver.com/")
        }
    }

}