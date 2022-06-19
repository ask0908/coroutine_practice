package com.example.kotlinprac.avatarview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityAvatarViewBinding
import io.getstream.avatarview.coil.loadImage

class AvatarViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAvatarViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_avatar_view)
        binding.run {
            lifecycleOwner = this@AvatarViewActivity
            avatarView.loadImage(R.drawable.image)
        }
    }
}