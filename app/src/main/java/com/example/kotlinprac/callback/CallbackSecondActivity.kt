package com.example.kotlinprac.callback

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityCallbackSecondBinding

class CallbackSecondActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private val binding by lazy {
        ActivityCallbackSecondBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            btnCallBottomSheet.setOnClickListener {
                val dialog = TestBottomSheet(this@CallbackSecondActivity, R.layout.test_bottom_sheet)
                dialog.apply {
                    setCallback(object : TestBottomSheet.OnSendFromBottomSheetDialog {
                        override fun sendValue(value: String) {
                            Log.e(TAG, "BottomSheetDialog -> 액티비티로 전달된 값 : $value")
                        }
                    })
                    showDialog()
                }
            }
        }
    }
}