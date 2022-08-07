package com.example.kotlinprac.recyclerview.clickevent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinprac.databinding.RowItemBinding

class CarAdapter(
    private val carList: ArrayList<Car>,
    private val clickListener: OnCarClickListener
): RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    private val TAG = this.javaClass.simpleName
    private lateinit var binding: RowItemBinding

    inner class CarViewHolder(
        binding: RowItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Car) {
            binding.model = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        binding = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item = carList[position]
        holder.bind(item)

        binding.root.setOnClickListener {
            clickListener.onCarClick(position, item)
        }
    }

    override fun getItemCount(): Int = carList.size

    interface OnCarClickListener {
        fun onCarClick(position: Int, car: Car)
    }

}