package com.example.kotlinprac.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityCoroutinePracticeBinding
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor

class CoroutineExampleActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private val binding by lazy {
        ActivityCoroutinePracticeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}