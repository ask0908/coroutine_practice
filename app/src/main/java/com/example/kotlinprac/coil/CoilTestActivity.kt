package com.example.kotlinprac.coil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.imageLoader
import coil.request.ImageRequest
import com.example.kotlinprac.databinding.ActivityCoilTestBinding

// https://images.velog.io/images/jojo_devstory/post/dae32386-bffc-40c3-b866-5c1e64516902/Android%2010_0.jpg
class CoilTestActivity : AppCompatActivity() {

    private val binding: ActivityCoilTestBinding by lazy {
        ActivityCoilTestBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val imageLoader = binding.imageView.context.imageLoader
        val request = ImageRequest.Builder(binding.imageView.context)
            .data("https://images.velog.io/images/jojo_devstory/post/dae32386-bffc-40c3-b866-5c1e64516902/Android%2010_0.jpg")
            .crossfade(true)
            .target(binding.imageView)
            .build()
        imageLoader.enqueue(request)

        /* BlurTransformation, GrayscaleTransformation은 2.0.0-rc01부터 삭제되서 쓸 수 없음. 쓰려면 직접 코드 가져와야 함 */
//        binding.imageView.load(R.drawable.image) {
//            transformations(CircleCropTransformation())
//        }
    }

}