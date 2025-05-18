package com.amontdevs.bluefrog.domain

sealed class BlueFrogResult<out T> {
    data class Success<out T>(
        val data: T,
    ) : BlueFrogResult<T>()

    data class Error(
        val exception: Exception,
    ) : BlueFrogResult<Nothing>()
}
