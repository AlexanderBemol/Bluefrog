package com.amontdevs.bluefrog.utils

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class NetworkConnectivityHelperTest {
    private val networkConnectivityHelper = NetworkConnectivityHelper()

    @Test
    fun `isNetworkAvailable should return true when internet is available`() =
        runBlocking {
            // When
            // NOTE: This test relies on the test environment having a live internet connection,
            // as it calls the actual platform API.
            val result = networkConnectivityHelper.isNetworkAvailable()

            // Then
            assertTrue(result, "Expected network to be available, but it was not.")
        }
}
