package com.amontdevs.bluefrog.domain

open class BlueFrogException(
    message: String,
) : Exception(message)

class NetworkNotAvailableException : BlueFrogException("Network is not available")
