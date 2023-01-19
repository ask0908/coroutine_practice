package com.example.kotlinprac.networkstate

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import timber.log.Timber

class NetworkStatusTracker(context: Context) {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkStatus = callbackFlow {
        val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onUnavailable() {
                super.onUnavailable()
                Timber.e("onUnavailable()")
                trySend(NetworkStatus.Unavailable).isSuccess
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Timber.e("onAvailable()")
                trySend(NetworkStatus.Available).isSuccess
            }

            override fun onLost(network: Network) {
                Timber.e("onLost()")
                trySend(NetworkStatus.Unavailable).isSuccess
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, networkStatusCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkStatusCallback)
        }
    }.distinctUntilChanged()
}