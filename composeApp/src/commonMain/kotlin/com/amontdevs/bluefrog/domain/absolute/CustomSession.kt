package com.amontdevs.bluefrog.domain.absolute

data class CustomSession(
    val isPredefined: Boolean = false,
    val title: String = "",
    val description: String = "",
    val notes: List<SessionAbsoluteNote> = emptyList(),
)
