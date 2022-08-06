package com.example.kotlinprac.other

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinprac.R

class OnBackPressedTestActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 뒤로가기 클릭 시 실행시킬 코드 입력
            Log.e(TAG, "뒤로가기 클릭")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_back_pressed_test)

        this.onBackPressedDispatcher.addCallback(this, callback)
    }
}