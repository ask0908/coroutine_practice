package com.example.kotlinprac.recyclerview.clickevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityRecyclerViewClickEventBinding

class RecyclerViewClickEventActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private lateinit var binding: ActivityRecyclerViewClickEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view_click_event)
        binding.run {
            val carList = arrayListOf(
                Car(
                    brand = "브랜드1",
                    model = 0,
                    isDiesel = true
                ),
                Car(
                    brand = "브랜드2",
                    model = 1,
                    isDiesel = false
                ),
                Car(
                    brand = "브랜드3",
                    model = 2,
                    isDiesel = true
                ),
                Car(
                    brand = "브랜드4",
                    model = 3,
                    isDiesel = false
                ),
                Car(
                    brand = "브랜드5",
                    model = 4,
                    isDiesel = true
                ),
                Car(
                    brand = "브랜드6",
                    model = 5,
                    isDiesel = false
                ),
            )
            val carAdapter = CarAdapter(carList, object : CarAdapter.OnCarClickListener {
                override fun onCarClick(position: Int, car: Car) {
                    val brand = car.brand
                    val model = car.model
                    val isDiesel = car.isDiesel
                    Log.e(TAG, "${position}번 아이템 클릭 - brand : $brand, model : $model, isDiesel : $isDiesel")
                }
            })
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@RecyclerViewClickEventActivity)
                setHasFixedSize(true)
                adapter = carAdapter
            }
        }
    }
}