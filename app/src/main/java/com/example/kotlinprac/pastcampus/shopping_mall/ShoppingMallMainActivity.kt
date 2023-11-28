package com.example.kotlinprac.pastcampus.shopping_mall

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityShoppingMallMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoppingMallMainActivity :
    BaseActivity<ActivityShoppingMallMainBinding>(R.layout.activity_shopping_mall_main) {

    private val adapter: PagingListAdapter by lazy { PagingListAdapter() }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            recyclerView.adapter = adapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.pagingData.collectLatest {
                if (it != null) {
                    adapter.submitData(lifecycle, it)
                }
            }
        }
    }
}