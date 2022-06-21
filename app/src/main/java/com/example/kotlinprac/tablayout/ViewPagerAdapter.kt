package com.example.kotlinprac.tablayout

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> TestFirstragment()
        1 -> TestSecondFragment()
        2 -> TestThirdFragment()
        3 -> TestFourthFragment()
        4 -> TestFifthFragment()
        else -> TestFirstragment()
    }
}