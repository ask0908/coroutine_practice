package com.example.kotlinprac.networkstate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers

class NetworkStatusViewModel(
    networkStatusTracker: NetworkStatusTracker
): ViewModel() {
    val state = networkStatusTracker.networkStatus.map(
        onAvailable = { MyState.Fetched },
        onUnavailable = { MyState.Error }
    ).asLiveData(Dispatchers.IO)
}