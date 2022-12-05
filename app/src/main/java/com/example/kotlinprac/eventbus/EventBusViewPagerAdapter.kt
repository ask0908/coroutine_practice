package com.example.kotlinprac.eventbus

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class EventBusViewPagerAdapter(
    activity: AppCompatActivity
): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> {
                EventBusFirstFragment()
            }
            1 -> {
                EventBusSecondFragment()
            }
            else -> {
                EventBusFirstFragment()
            }
        }

        return fragment
    }

}