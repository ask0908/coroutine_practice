package com.example.kotlinprac.textwatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityTextWatcherTestBinding

class TextWatcherTestActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private lateinit var binding: ActivityTextWatcherTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_text_watcher_test)
        binding.run {
            lifecycleOwner = this@TextWatcherTestActivity

            val commonTextWatcher = CommonTextWatcher(
                afterChanged = { source ->
                    Log.e(TAG, "source : $source")
                }
            )

            etFirst.addTextChangedListener(commonTextWatcher)
            etSecond.addTextChangedListener(commonTextWatcher)
        }
    }
}