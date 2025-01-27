package com.mydia.restaurantsmartqr.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class Network @Inject constructor(val context: Context) : NetworkConnectivity {

    val networkStatusObserver = MutableLiveData<NetworkStatus>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun isConnected(): Boolean {
        var connected = false
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    networkStatusObserver.postValue(NetworkStatus.Available)
                    connected = true
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    networkStatusObserver.postValue(NetworkStatus.Unavailable)
                    connected = false
                }
            })
            runBlocking { delay(10) }
            return connected
        } catch (e: Exception) {
            e.printStackTrace()
            connected = false
            return connected
        }
    }
}

interface NetworkConnectivity {
    fun isConnected(): Boolean
}