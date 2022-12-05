package com.example.kotlinprac.eventbus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinprac.databinding.ActivityEventBusTestBinding
import org.greenrobot.eventbus.EventBus

class EventBusTestActivity : AppCompatActivity() {

    private val binding: ActivityEventBusTestBinding by lazy {
        ActivityEventBusTestBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.run {
            vpTest.run {
                offscreenPageLimit = 1
                adapter = EventBusViewPagerAdapter(this@EventBusTestActivity)
            }

            btnFirstFragment.setOnClickListener {
                EventBus.getDefault().post(User(name = "user"))
                vpTest.currentItem = 0
            }
            btnSecondFragment.setOnClickListener {
                EventBus.getDefault().post(User(name = "user2"))
                vpTest.currentItem = 1
            }
        }
    }

}