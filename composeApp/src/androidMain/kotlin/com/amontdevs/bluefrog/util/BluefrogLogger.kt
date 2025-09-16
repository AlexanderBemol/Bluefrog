package com.amontdevs.bluefrog.util

import android.util.Log

class BluefrogLogger: IBluefrogLogger {

    override fun d(message: String, tag: String?) {
        Log.d(tag, message)
    }

    override fun e(message: String, tag: String?) {
        Log.e(tag, message)
    }

    override fun i(message: String, tag: String?) {
        Log.i(tag, message)
    }
}