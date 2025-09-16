package com.amontdevs.bluefrog.util

interface IBluefrogLogger {
    fun d(message: String, tag: String? = null)
    fun e(message: String, tag: String? = null)
    fun i(message: String, tag: String? = null)
}