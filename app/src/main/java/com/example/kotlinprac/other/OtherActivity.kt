package com.example.kotlinprac.other

import android.os.Bundle
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityOtherBinding

class OtherActivity : BaseActivity<ActivityOtherBinding>(R.layout.activity_other) {

    private val bottomSheet: MyBottomSheet by lazy {
        MyBottomSheet("제목")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            btnShowBottomSheet.setOnClickListener {
                bottomSheet.show(supportFragmentManager, MyBottomSheet::class.java.simpleName)
            }
        }
    }
}