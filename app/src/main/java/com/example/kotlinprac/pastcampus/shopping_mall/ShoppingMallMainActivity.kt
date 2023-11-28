package com.example.kotlinprac.pastcampus.shopping_mall

import android.os.Bundle
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityShoppingMallMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingMallMainActivity :
    BaseActivity<ActivityShoppingMallMainBinding>(R.layout.activity_shopping_mall_main) {

    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            recyclerView.adapter = adapter
        }
    }
}