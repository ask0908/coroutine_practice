package com.example.kotlinprac.navigation2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityNavigationTestBinding

class NavigationTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation_test)

        binding.run {
            binding.lifecycleOwner = this@NavigationTestActivity
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host2) as NavHostFragment
            val navController = navHostFragment.navController
            NavigationUI.setupWithNavController(navBar2, navController)
        }
    }
}