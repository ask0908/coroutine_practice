package com.example.kotlinprac.recyclerview.bindingadapter

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityBindingAdapterTestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BindingAdapterTestActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private val imageViewModel: ImageViewModel by viewModels()

    private lateinit var binding: ActivityBindingAdapterTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_binding_adapter_test)
        binding.run {
            lifecycleOwner = this@BindingAdapterTestActivity

            getImages(1, "exaamTZ6k0zbst2JhhAidmJYAU66-Y1VRjclyeC1Gpc")
        }
    }

    private fun getImages(page: Int, clientId: String) {
        lifecycleScope.launch {
            imageViewModel.apply {
                this.getImages(page, clientId)
                imageResponse.collect {
                    when (it) {
                        is ApiState.Success -> {
                            it.data?.let { data ->
                                val result = data
                                binding.rvImage.apply {
                                    layoutManager = LinearLayoutManager(this@BindingAdapterTestActivity)
                                    setHasFixedSize(true)
                                    adapter = ImageAdapter(this@BindingAdapterTestActivity, result) { imageDTO ->
                                        Log.e(TAG, "regular : ${imageDTO.imageUrls.regular}")
                                    }
                                }
                            }
                            mImageResponse.value = ApiState.Loading()
                        }
                        is ApiState.Error -> {
                            Log.e(TAG, "에러 : ${it.message}")
                            mImageResponse.value = ApiState.Loading()
                        }
                        is ApiState.Loading -> {}
                    }
                }
            }
        }
    }

}