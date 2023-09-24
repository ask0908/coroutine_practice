package com.example.kotlinprac.recyclerview.uitest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinprac.databinding.TextRowItemBinding

class CustomAdapter(
    private val list: List<String>
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private lateinit var binding: TextRowItemBinding

    inner class ViewHolder(binding: TextRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: String) {
                binding.text = item
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            TextRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}