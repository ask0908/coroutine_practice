package com.example.kotlinprac.youtube

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import com.example.kotlinprac.R

class ChromeClient(private val mActivity: Activity) : WebChromeClient() {
    private var mCustomView: View? = null
    override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
        result.confirm()
        return super.onJsAlert(view, url, message, result)
    }

    private var mOriginalOrientation = 0
    private var mFullscreenContainer: FullscreenHolder? = null
    private var mCustomViewCollback: CustomViewCallback? = null
    override fun onShowCustomView(view: View, callback: CustomViewCallback) {
        if (mCustomView != null) {
            callback.onCustomViewHidden()
            return
        }
        mOriginalOrientation = mActivity.requestedOrientation
        val decor = mActivity.window.decorView as FrameLayout
        mFullscreenContainer = FullscreenHolder(mActivity)
        mFullscreenContainer!!.addView(view, ViewGroup.LayoutParams.MATCH_PARENT)
        decor.addView(mFullscreenContainer, ViewGroup.LayoutParams.MATCH_PARENT)
        mCustomView = view
        mCustomViewCollback = callback
        mActivity.requestedOrientation = mOriginalOrientation
    }

    override fun onHideCustomView() {
        if (mCustomView == null) {
            return
        }
        val decor = mActivity.window.decorView as FrameLayout
        decor.removeView(mFullscreenContainer)
        mFullscreenContainer = null
        mCustomView = null
        mCustomViewCollback!!.onCustomViewHidden()
        mActivity.requestedOrientation = mOriginalOrientation
    }

    internal class FullscreenHolder(ctx: Context) : FrameLayout(ctx) {
        override fun onTouchEvent(evt: MotionEvent): Boolean {
            return true
        }

        init {
            setBackgroundColor(ctx.resources.getColor(R.color.black))
        }
    }
}