package com.amontdevs.bluefrog.util

import platform.Foundation.NSLog

class BluefrogLogger : IBluefrogLogger {
    override fun d(message: String, tag: String?) {
        NSLog("DEBUG [${tag ?: ""}]- $message")
    }

    override fun e(message: String, tag: String?) {
        NSLog("ERROR [${tag ?: ""}]- $message")
    }

    override fun i(message: String, tag: String?) {
        NSLog("INFO [${tag ?: ""}]- $message")
    }
}
