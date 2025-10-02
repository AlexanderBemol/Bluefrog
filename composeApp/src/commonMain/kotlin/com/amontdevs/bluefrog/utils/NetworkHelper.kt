package com.amontdevs.bluefrog.utils

expect class NetworkConnectivityHelper {
    suspend fun isNetworkAvailable(): Boolean
}