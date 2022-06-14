package com.example.kotlinprac.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityNavigationMainBinding

class NavigationMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            NavigationUI.setupWithNavController(navBar, findNavController(R.id.nav_host))
        }
    }
}