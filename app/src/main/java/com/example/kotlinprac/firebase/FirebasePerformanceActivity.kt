package com.example.kotlinprac.firebase

import android.os.Bundle
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityFirebasePerformanceBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.AddTrace
import com.google.firebase.perf.metrics.Trace
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class FirebasePerformanceActivity :
    BaseActivity<ActivityFirebasePerformanceBinding>(R.layout.activity_firebase_performance) {

    private lateinit var trace: Trace

    @AddTrace(name = "onCreateTest")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            btnPerformance.setOnClickListener {
                trace = Firebase.performance.newTrace("trace start")
                trace.start()
                trace.incrementMetric("test_metric_name", 2)
                trace.incrementMetric("second_test_metric_name", 2)
                CoroutineScope(Dispatchers.IO).launch {
                    delay(3000L)
                    Timber.e("## 3초 대기 끝!")
                    trace.stop()
                    Timber.e("## Trace 완료")
                }
            }
        }
    }
}