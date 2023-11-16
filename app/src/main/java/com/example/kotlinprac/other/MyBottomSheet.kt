package com.example.kotlinprac.other

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.MyBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottomSheet(
    private var bottomSheetTitle: String
) : BottomSheetDialogFragment() {

    private lateinit var binding: MyBottomSheetDialogBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MyBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val bottomSheet = dialog?.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title = bottomSheetTitle
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    private fun initWebView() = binding.wbBottomSheet.apply {
        settings.run {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowContentAccess = true
        }
        webViewClient = WebViewClient()
        webChromeClient = WebChromeClient()
        loadUrl("https://m.naver.com")

        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    // 스크롤 중 드래그 방지
                    bottomSheetBehavior.isDraggable = false
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // 스크롤이 끝나면 드래그
                    bottomSheetBehavior.isDraggable = true
                }
            }
            false
        }
    }

}