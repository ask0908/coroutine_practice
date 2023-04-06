package com.example.kotlinprac.networkstate.one

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinprac.databinding.ActivityNetworkStateTestBinding

class NetworkStateTestActivity : AppCompatActivity() {

    private val binding: ActivityNetworkStateTestBinding by lazy {
        ActivityNetworkStateTestBinding.inflate(layoutInflater)
    }
    private val viewModel: NetworkStatusViewModel by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val networkStatusTracker = NetworkStatusTracker(this@NetworkStateTestActivity)
                return NetworkStatusViewModel(networkStatusTracker) as T
            }
        })[NetworkStatusViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.state.observe(this) { state ->
            when (state) {
                MyState.Fetched -> {
                    binding.layoutConnected.visibility = VISIBLE
                    binding.layoutDisconnected.visibility = GONE
                }
                MyState.Error -> {
                    binding.layoutConnected.visibility = GONE
                    binding.layoutDisconnected.visibility = VISIBLE
                }
            }
        }
    }
}