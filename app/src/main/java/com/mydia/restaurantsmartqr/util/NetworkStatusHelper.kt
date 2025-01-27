package com.mydia.restaurantsmartqr.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData


class NetworkStatusHelper(private val context: Context) : LiveData<NetworkStatus>() {
    var connectivityManager: ConnectivityManager =    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val valideNetworkConnections : ArrayList<Network> = ArrayList()


    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    fun announceStatus(){
        if (valideNetworkConnections.isNotEmpty()){
            postValue(NetworkStatus.Available)
        } else {
            postValue(NetworkStatus.Unavailable)
        }
    }

    private fun getConnectivityManagerCallback() = object : ConnectivityManager.NetworkCallback(){
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                val networkCapability = connectivityManager.getNetworkCapabilities(network)
                val hasNetworkConnection = networkCapability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)?:false
                if (hasNetworkConnection){
                    valideNetworkConnections.add(network)
                    announceStatus()
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                valideNetworkConnections.remove(network)
                announceStatus()
            }

            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                    valideNetworkConnections.add(network)
                } else {
                    valideNetworkConnections.remove(network)
                }
                announceStatus()
            }
        }


    override fun onActive() {
        super.onActive()
        connectivityManagerCallback = getConnectivityManagerCallback()
        val networkRequest = NetworkRequest
            .Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)

    }
}
