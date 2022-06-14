package com.example.kotlinprac.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityViewPagerBinding

class ViewPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPagerBinding

    private val sliderImageHandler: Handler = Handler()
    private val sliderImageRunnable = Runnable { binding.imageViewpager.currentItem = binding.imageViewpager.currentItem + 1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageList = arrayListOf<Int>().apply {
            for (i in 0..2) {
                add(R.drawable.image)
            }
        }

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pageWidth)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        binding.imageViewpager.apply {
            adapter = ViewPagerAdapter(imageList, binding.imageViewpager)
            offscreenPageLimit = 1
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderImageHandler.removeCallbacks(sliderImageRunnable)
                    sliderImageHandler.postDelayed(sliderImageRunnable, 1000)
                }
            })
            setPageTransformer { page, position ->
                page.translationX = position * -offsetPx
            }
        }
    }

    // 다른 페이지로 이동했다가 돌아오면 다시 배너 자동 무한 스크롤을 시작한다
    override fun onResume() {
        super.onResume()
        sliderImageHandler.postDelayed(sliderImageRunnable, 1000)
    }

    // 다른 액티비티가 메인을 가리는 동안 배너가 자동 무한 스크롤될 필요는 없기 때문에 스크롤을 중단시킨다
    override fun onPause() {
        super.onPause()
        sliderImageHandler.removeCallbacks(sliderImageRunnable)
    }

}