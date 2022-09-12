package com.example.kotlinprac.recyclerview.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object BindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun bindImageToView(imageView: ImageView, url: String?) =
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
}