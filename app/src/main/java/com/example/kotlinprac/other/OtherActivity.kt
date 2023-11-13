package com.example.kotlinprac.other

import android.net.Uri
import android.os.Bundle
import androidx.core.net.toUri
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityOtherBinding
import timber.log.Timber

class OtherActivity : BaseActivity<ActivityOtherBinding>(R.layout.activity_other) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deepLink: Uri = "symbol://enterWindow?header=header&body=body&url=https://example.co.kr/enter?a=123&b=456&c=789".toUri()
        val encodedDeeplink = encodeUrlQueryParameter(deepLink.toString())
        if (encodedDeeplink.isNotEmpty()) {
            val urlValue = Uri.parse(encodedDeeplink).getQueryParameter("url")
            Timber.e("## urlValue : $urlValue")
        }
    }

    fun encodeUrlQueryParameter(deepLink: String): String {
        val urlIndex = deepLink.indexOf("url=")
        if (urlIndex == -1) return ""

        val beforeUrl = deepLink.substring(0, urlIndex + 4)
        val urlAndAfter = deepLink.substring(urlIndex + 4)

        val firstAmpIndex = urlAndAfter.indexOf("&")
        if (firstAmpIndex == -1) return ""

        val urlParam = urlAndAfter.substring(0, firstAmpIndex)
        val afterUrlParam = urlAndAfter.substring(firstAmpIndex).replace("&", "%26")

        return beforeUrl + urlParam + afterUrlParam
    }
}