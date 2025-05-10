package com.amontdevs.bluefrog.ui.screens.home

import com.amontdevs.bluefrog.domain.AbsoluteNote

data class ManualModeState(
    val sessions: List<CustomSession> = emptyList(),
)

data class CustomSession(
    val isPredefined: Boolean = false,
    val title: String = "",
    val description: String = "",
    val notes: List<AbsoluteNote> = emptyList(),
)