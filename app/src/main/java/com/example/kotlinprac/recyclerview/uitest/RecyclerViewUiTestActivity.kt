package com.example.kotlinprac.recyclerview.uitest

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityRecyclerViewUiTestBinding

class RecyclerViewUiTestActivity :
    BaseActivity<ActivityRecyclerViewUiTestBinding>(R.layout.activity_recycler_view_ui_test) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSet: MutableList<String> = mutableListOf()
        for (i in 0 until 10) {
            dataSet.add("글자 $i")
        }

        bind {
            rvTest.apply {
                layoutManager = LinearLayoutManager(this@RecyclerViewUiTestActivity)

                val adapter = CustomAdapter(dataSet)
                setAdapter(adapter)
            }
        }
    }
}