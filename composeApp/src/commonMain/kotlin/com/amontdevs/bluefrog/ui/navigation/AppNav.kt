package com.amontdevs.bluefrog.ui.navigation

import kotlinx.serialization.Serializable

sealed class AppNav {
    @Serializable
    data class AbsoluteSession(
        val sessionId: Int,
    ) : AppNav()

    @Serializable
    object AbsoluteManualMode : AppNav()

    @Serializable
    object CustomSessionDialog : AppNav()
}

fun String.camelToUnderScore(): String {
    if (this.isEmpty()) {
        return ""
    }
    val builder = StringBuilder()
    builder.append(this[0].lowercaseChar())

    for (i in 1 until this.length) {
        val char = this[i]
        if (char.isUpperCase()) {
            builder.append('_')
            builder.append(char.lowercaseChar())
        } else {
            builder.append(char)
        }
    }
    return builder.toString()
}
