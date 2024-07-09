package com.example.swiftbargain.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class InternetConnectivityChecker(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isInternetAvailable(context: Context): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }
}
