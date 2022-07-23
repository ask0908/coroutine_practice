package com.example.kotlinprac.callback

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinprac.databinding.ActivityCallbackFirstBinding

class CallbackFirstActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private val binding by lazy {
        ActivityCallbackFirstBinding.inflate(layoutInflater)
    }
    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val list = arrayListOf(
            Person("test1", "1"),
            Person("test2", "2"),
            Person("test3", "3"),
            Person("test4", "4"),
            Person("test5", "5"),
            Person("test6", "6"),
            Person("test7", "7"),
            Person("test8", "8"),
            Person("test9", "9"),
            Person("test10", "10"),
            Person("test11", "11"),
            Person("test12", "12"),
        )

        personAdapter = PersonAdapter(this@CallbackFirstActivity, list).apply {
            setOnStateInterface(object : OnSendStateInterface {
                override fun sendValue(value: String, position: Int) {
                    Log.e(TAG, "콜백으로 받은 값 : $value, position : $position")
                }
            })
        }

        binding.run {
            rvPerson.apply {
                layoutManager = LinearLayoutManager(this@CallbackFirstActivity)
                setHasFixedSize(true)
                adapter = personAdapter
            }
        }
    }

}