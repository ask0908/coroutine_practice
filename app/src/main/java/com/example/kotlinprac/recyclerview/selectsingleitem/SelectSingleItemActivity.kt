package com.example.kotlinprac.recyclerview.selectsingleitem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivitySelectSingleItemBinding

class SelectSingleItemActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private lateinit var binding: ActivitySelectSingleItemBinding
    private var list = arrayListOf<SimpleModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_single_item)
        binding.run {
            lifecycleOwner = this@SelectSingleItemActivity

            for (i in 0..10) {
                list.add(SimpleModel(title = "테스트$i", isSelected = false))
            }

            recyclerview.apply {
                layoutManager = LinearLayoutManager(this@SelectSingleItemActivity)
                setHasFixedSize(true)
                adapter = SelectSingleItemAdapter(this@SelectSingleItemActivity, list).apply {
                    setOnItemClickListener(object : SelectSingleItemAdapter.OnItemClickListener {
                        override fun onItemClick(item: SimpleModel, position: Int) {
                            Toast.makeText(this@SelectSingleItemActivity, "선택한 문자열 : ${item.title}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }
}