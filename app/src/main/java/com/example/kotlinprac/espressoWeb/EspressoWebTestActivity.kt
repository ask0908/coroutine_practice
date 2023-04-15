package com.example.kotlinprac.espressoWeb

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityEspressoWebTestBinding

class EspressoWebTestActivity :
    BaseActivity<ActivityEspressoWebTestBinding>(R.layout.activity_espresso_web_test) {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind {
            webView.apply {
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {}
                webChromeClient = object : WebChromeClient() {}
                loadUrl("https://www.aladin.co.kr/home/welcome.aspx")
            }
        }
    }
}