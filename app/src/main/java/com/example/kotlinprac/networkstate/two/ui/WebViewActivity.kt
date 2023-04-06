package com.example.kotlinprac.networkstate.two.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinprac.databinding.ActivityWebViewBinding
import com.example.kotlinprac.networkstate.two.utils.DialogUtils

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val articleUrl = intent.getStringExtra("articleUrl")
        if (!articleUrl.isNullOrEmpty())
            binding.webView.loadUrl(articleUrl)
        else DialogUtils(this).showDialog("Error", "Article Url Not Found")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}