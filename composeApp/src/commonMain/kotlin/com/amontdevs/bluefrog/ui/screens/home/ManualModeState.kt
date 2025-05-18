package com.amontdevs.bluefrog.ui.screens.home

import com.amontdevs.bluefrog.domain.absolute.CustomSession

data class ManualModeState(
    val sessions: List<CustomSession> = emptyList(),
)
