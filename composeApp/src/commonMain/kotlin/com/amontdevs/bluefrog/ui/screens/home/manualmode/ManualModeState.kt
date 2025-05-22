package com.amontdevs.bluefrog.ui.screens.home.manualmode

import com.amontdevs.bluefrog.domain.absolute.CustomSession

data class ManualModeState(
    val sessions: List<CustomSession> = emptyList(),
    val sessionsFilter: SessionsFilter = SessionsFilter.All,
)

enum class SessionsFilter {
    All,
    Predefined,
    Custom,
    ;

    override fun toString(): String =
        when (this) {
            All -> "All"
            Predefined -> "Predefined"
            Custom -> "Custom"
        }
}
