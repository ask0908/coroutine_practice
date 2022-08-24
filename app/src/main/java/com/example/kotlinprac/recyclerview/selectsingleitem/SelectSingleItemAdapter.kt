package com.example.kotlinprac.recyclerview.selectsingleitem

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ItemSingleItemBinding

class SelectSingleItemAdapter(
    private val context: Context,
    private val list: ArrayList<SimpleModel>,
): RecyclerView.Adapter<SelectSingleItemAdapter.SelectSingleItemViewHolder>() {

    private lateinit var binding: ItemSingleItemBinding
    private var onItemClickListener: OnItemClickListener? = null

    private var selectedPosition = 0

    interface OnItemClickListener {
        fun onItemClick(item: SimpleModel, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class SelectSingleItemViewHolder(
        private val binding: ItemSingleItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SimpleModel) {
            binding.model = item

            if (selectedPosition == absoluteAdapterPosition) {
                list[absoluteAdapterPosition].isSelected = true
                binding.setChecked()
            } else {
                list[absoluteAdapterPosition].isSelected = false
                binding.setUnchecked()
            }

            if (onItemClickListener != null) {
                binding.tvTitle.setOnClickListener {
                    onItemClickListener?.onItemClick(item, absoluteAdapterPosition)
                    if (selectedPosition != absoluteAdapterPosition) {
                        binding.setChecked()
                        notifyItemChanged(selectedPosition)
                        selectedPosition = absoluteAdapterPosition
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectSingleItemViewHolder {
        binding = ItemSingleItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return SelectSingleItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectSingleItemViewHolder, position: Int) {
        val item = list[position]
        if (position == 0) {
            binding.setChecked()
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    private fun ItemSingleItemBinding.setChecked() = tvTitle.setTextColor(ContextCompat.getColor(context, R.color.purple_200))

    private fun ItemSingleItemBinding.setUnchecked() = tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black))

}