package com.amontdevs.bluefrog.util

class BluefrogLogger : IBluefrogLogger {
    override fun d(
        message: String,
        tag: String?,
    ) {
        println("DEBUG [${tag ?: ""}]- $message")
    }

    override fun e(
        message: String,
        tag: String?,
    ) {
        println("ERROR [${tag ?: ""}]- $message")
    }

    override fun i(
        message: String,
        tag: String?,
    ) {
        println("INFO [${tag ?: ""}]- $message")
    }
}