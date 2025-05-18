package com.amontdevs.bluefrog.ui.navigation

import kotlinx.serialization.Serializable

sealed class AppNav {
    @Serializable
    data class AbsoluteSession(
        val sessionId: Int,
    ) : AppNav()

    @Serializable
    object AbsoluteManualMode : AppNav()
}
