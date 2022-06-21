package com.example.kotlinprac.tablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityTabBinding
import com.google.android.material.tabs.TabLayoutMediator

class TabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab)
        binding.run {
            lifecycleOwner = this@TabActivity
            viewpager2.apply {
                adapter = ViewPagerAdapter(this@TabActivity)
            }
            TabLayoutMediator(tabLayout, viewpager2) { tab, position ->
                when (position) {
                    0 -> tab.text = "탭 1"
                    1 -> tab.text = "탭 2"
                    2 -> tab.text = "탭 3"
                    3 -> tab.text = "탭 4"
                    4 -> tab.text = "탭 5"
                }
            }.attach()
        }
    }
}