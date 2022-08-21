package com.example.kotlinprac.recyclerview.stickyheader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityStickyHeaderRecyclerViewBinding

class StickyHeaderRecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStickyHeaderRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sticky_header_recycler_view)
        binding.run {
            lifecycleOwner = this@StickyHeaderRecyclerViewActivity

            rvStickyHeader.apply {
                layoutManager = LinearLayoutManager(this@StickyHeaderRecyclerViewActivity)
                setHasFixedSize(true)
                addItemDecoration(StickyHeaderDecoration())
                adapter = Adapter(getItems())
            }
        }
    }

    private fun getItems(): ArrayList<StickyItem> {

        val items: ArrayList<StickyItem> = ArrayList()

        items.add(StickyItem("Korea", NormalItem("1번째 아이템", "010-1234-5678", 30), true))
        for (i in 1..10) {
            items.add(StickyItem("", NormalItem("1번째 아이템", "010-1234-5678", 30), false))
        }

        items.add(StickyItem("China", NormalItem("2번째 아이템", "010-1234-5678", 30), true))
        for (i in 1..10) {
            items.add(StickyItem("", NormalItem("2번째 아이템", "010-1234-5678", 30), false))
        }

        items.add(StickyItem("Japan", NormalItem("3번째 아이템", "010-1234-5678", 30), true))
        for (i in 1..10) {
            items.add(StickyItem("", NormalItem("3번째 아이템", "010-1234-5678", 30), false))
        }

        return items
    }

}