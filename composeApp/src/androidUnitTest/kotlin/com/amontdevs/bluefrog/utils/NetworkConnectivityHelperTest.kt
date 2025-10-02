package com.amontdevs.bluefrog.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowConnectivityManager

@RunWith(AndroidJUnit4::class)
class NetworkConnectivityHelperTest {

    private lateinit var context: Context
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var shadowConnectivityManager: ShadowConnectivityManager
    private lateinit var networkConnectivityHelper: NetworkConnectivityHelper

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        shadowConnectivityManager = Shadows.shadowOf(connectivityManager)
        networkConnectivityHelper = NetworkConnectivityHelper(context)
    }

    @Test
    fun `isNetworkAvailable returns true when wifi is available`() = runBlocking {
        // Given
        val network: Network = mock()
        val networkCapabilities: NetworkCapabilities = mock()
        shadowConnectivityManager.setActiveNetworkInfo(connectivityManager.activeNetworkInfo)
        whenever(connectivityManager.activeNetwork).thenReturn(network)
        whenever(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        whenever(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(true)

        // When
        // NOTE: This test assumes an internet connection is available, as it will attempt a real socket connection.
        // In a controlled test environment, this might require network sandboxing or mocking at a lower level.
        val result = networkConnectivityHelper.isNetworkAvailable()

        // Then
        assert(result)
    }

    @Test
    fun `isNetworkAvailable returns true when cellular is available`() = runBlocking {
        // Given
        val network: Network = mock()
        val networkCapabilities: NetworkCapabilities = mock()
        shadowConnectivityManager.setActiveNetworkInfo(connectivityManager.activeNetworkInfo)
        whenever(connectivityManager.activeNetwork).thenReturn(network)
        whenever(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        whenever(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)).thenReturn(true)

        // When
        // NOTE: This test assumes an internet connection is available.
        val result = networkConnectivityHelper.isNetworkAvailable()

        // Then
        assert(result)
    }

    @Test
    fun `isNetworkAvailable returns false when network is not available`() = runBlocking {
        // Given
        shadowConnectivityManager.setActiveNetworkInfo(null)
        whenever(connectivityManager.activeNetwork).thenReturn(null)

        // When
        val result = networkConnectivityHelper.isNetworkAvailable()

        // Then
        assert(!result)
    }

    @Test
    fun `isNetworkAvailable returns false when network capabilities are null`() = runBlocking {
        // Given
        val network: Network = mock()
        shadowConnectivityManager.setActiveNetworkInfo(connectivityManager.activeNetworkInfo)
        whenever(connectivityManager.activeNetwork).thenReturn(network)
        whenever(connectivityManager.getNetworkCapabilities(network)).thenReturn(null)

        // When
        val result = networkConnectivityHelper.isNetworkAvailable()

        // Then
        assert(!result)
    }
}
