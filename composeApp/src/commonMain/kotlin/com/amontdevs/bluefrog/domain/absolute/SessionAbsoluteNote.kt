package com.amontdevs.bluefrog.domain.absolute

data class SessionAbsoluteNote(
    val absoluteNote: AbsoluteNote = AbsoluteNote.C3,
    val isSessionMainNote: Boolean = false,
)
