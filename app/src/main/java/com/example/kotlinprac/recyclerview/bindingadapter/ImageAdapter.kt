package com.example.kotlinprac.recyclerview.bindingadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinprac.databinding.ItemImageBinding

class ImageAdapter(
    private val context: Context,
    private val list: List<ImageDTO>,
    private val clickListener: (ImageDTO) -> Unit
): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private lateinit var binding: ItemImageBinding

    inner class ImageViewHolder(
        private val binding: ItemImageBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageDTO) {
            binding.run {
                model = item.imageUrls
                root.setOnClickListener {
                    clickListener(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        binding = ItemImageBinding.inflate(LayoutInflater.from(context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}