package com.example.kotlinprac.coroutine_and_hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlinprac.R
import com.example.kotlinprac.coroutine_and_hilt.viewmodels.ApiViewModel
import com.example.kotlinprac.databinding.ActivityCoroutineHiltTestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoroutineHiltTestActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private lateinit var binding: ActivityCoroutineHiltTestBinding
    private val viewModel: ApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coroutine_hilt_test)
        binding.run {
            lifecycleOwner = this@CoroutineHiltTestActivity
            getPosts()
        }
    }

    private fun getPosts() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    getPosts()
                    response.collect {
                        when (it) {
                            is ApiState.Success -> {
                                Log.e(TAG, "받아온 데이터 : ${it.data}")
                                binding.textview.text = "데이터 받아오기 성공!!"
                            }
                            is ApiState.Error -> {
                                Log.e(TAG, "데이터 받아오기 실패 : ${it.message}")
                                binding.textview.text = "데이터 받아오기 실패"
                            }
                            is ApiState.Loading -> {
                                binding.textview.text = "데이터 받아오는 중..."
                            }
                        }
                    }
                }
            }
        }
    }
}