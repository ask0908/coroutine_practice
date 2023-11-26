package com.example.kotlinprac.pastcampus.image_contract.mvvm

import android.os.Bundle
import androidx.activity.viewModels
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityImageContractMainBinding
import com.example.kotlinprac.pastcampus.image_contract.mvvm.repository.ImageRepositoryImpl

class ImageContractMainActivity :
    BaseActivity<ActivityImageContractMainBinding>(R.layout.activity_image_contract_main) {

    private val viewModel = MvvmViewModel(ImageRepositoryImpl())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.view = this
        binding.viewModel = viewModel
    }

    fun loadImage() {
        viewModel.loadRandomImage()
    }
}