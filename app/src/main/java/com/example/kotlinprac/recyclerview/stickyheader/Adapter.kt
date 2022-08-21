package com.example.kotlinprac.recyclerview.stickyheader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinprac.databinding.NormalItemBinding
import com.example.kotlinprac.databinding.StickyItemBinding

private const val STICKY_TYPE = 0
private const val NORMAL_TYPE = 1

class Adapter(var items: List<StickyItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var stickyBinding: StickyItemBinding
    private lateinit var normalItemBinding: NormalItemBinding

    // "2022.12.31 금" 부분을 표시할 뷰홀더
    inner class StickyViewHolder2(private val binding: StickyItemBinding): RecyclerView.ViewHolder(binding.root), StickyHeader {
        fun bind(item: StickyItem) {
            binding.run {
                model = item
                tvCountry.text = item.country   // 희한하게 이 코드가 있어야 헤더가 보인다
            }
        }

        // 이 부분의 커스텀 게터가 data class에 있는 일자를 받게 한다
        override val stickyId: String
            get() = items[absoluteAdapterPosition].country

    }

    inner class NormalViewHolder2(private val binding: NormalItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NormalItem) {
            binding.run {
                model = item
            }
        }
    }

    override fun getItemViewType(position: Int): Int = if (items[position].isSticky) {
        STICKY_TYPE
    } else {
        NORMAL_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        stickyBinding = StickyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        normalItemBinding = NormalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return if (viewType == STICKY_TYPE) {
            StickyViewHolder2(stickyBinding)
        } else {
            NormalViewHolder2(normalItemBinding)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (holder is StickyViewHolder2) {
            holder.bind(item)
        } else if (holder is NormalViewHolder2) {
            holder.bind(item.normalItem)
        }

    }

}