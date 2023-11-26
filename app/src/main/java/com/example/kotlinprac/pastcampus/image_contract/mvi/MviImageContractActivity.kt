package com.example.kotlinprac.pastcampus.image_contract.mvi

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityMviImageContractBinding
import com.example.kotlinprac.pastcampus.image_contract.mvi.repository.ImageRepositoryImpl
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MviImageContractActivity :
    BaseActivity<ActivityMviImageContractBinding>(R.layout.activity_mvi_image_contract) {

    private val viewModel: MviViewModel by viewModels {
        // ImageRepositoryImpl은 아무것도 넣지 않으면 Dispatchers.IO로 설정됨
        MviViewModel.MviViewModelFactory(ImageRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            view = this@MviImageContractActivity
        }
        observeViewModel()
    }

    fun loadImage() {
        lifecycleScope.launch {
            // 인텐트 통해 뷰모델에 전달
            viewModel.channel.send(MviIntent.LoadImage)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is MviState.Idle -> {
                        binding.progressView.isVisible = false
                    }
                    is MviState.Loading -> {
                        binding.progressView.isVisible = true
                    }
                    is MviState.LoadedImage -> {
                        binding.progressView.isVisible = false
                        binding.imageView.run {
                            setBackgroundColor(Color.parseColor(state.image.color))
                            load(state.image.url) {
                                crossfade(300)
                            }
                        }
                        binding.imageCountTextView.text = "불러온 이미지 수 : ${state.count}"
                    }
                }
            }
        }
    }
}