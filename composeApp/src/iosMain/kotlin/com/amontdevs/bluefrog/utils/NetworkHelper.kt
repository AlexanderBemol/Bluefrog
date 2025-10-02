@file:OptIn(ExperimentalForeignApi::class)

package com.amontdevs.bluefrog.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.SystemConfiguration.SCNetworkReachabilityCreateWithName
import platform.SystemConfiguration.SCNetworkReachabilityFlagsVar
import platform.SystemConfiguration.SCNetworkReachabilityGetFlags
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsReachable
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class NetworkConnectivityHelper {
    actual suspend fun isNetworkAvailable(): Boolean =
        withContext(Dispatchers.Default) {
            suspendCoroutine { continuation ->
                val reachability = SCNetworkReachabilityCreateWithName(null, "8.8.8.8")
                if (reachability == null) {
                    continuation.resume(false)
                    return@suspendCoroutine
                }

                memScoped {
                    val flags = alloc<SCNetworkReachabilityFlagsVar>()
                    if (!SCNetworkReachabilityGetFlags(reachability, flags.ptr)) {
                        continuation.resume(false)
                        return@memScoped
                    }

                    val isReachable = (flags.value and kSCNetworkReachabilityFlagsReachable) != 0u
                    continuation.resume(isReachable)
                }
            }
        }
}
