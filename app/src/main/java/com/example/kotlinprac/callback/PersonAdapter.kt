package com.example.kotlinprac.callback

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinprac.databinding.ItemPersonBinding

class PersonAdapter(
    private val context: Context,
    private val list: ArrayList<Person>
): RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    private lateinit var binding: ItemPersonBinding
    private var listener: OnSendStateInterface? = null

    inner class PersonViewHolder(binding: ItemPersonBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Person) {
            binding.model = item

            binding.tvPersonName.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    if (listener == null) return@setOnClickListener
                    listener?.sendValue(item.age, pos)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        binding = ItemPersonBinding.inflate(LayoutInflater.from(context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    fun setOnStateInterface(listener: OnSendStateInterface) {
        this.listener = listener
    }
}