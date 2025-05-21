package com.amontdevs.bluefrog.domain.absolute

import androidx.room.Entity

@Entity
data class SessionAbsoluteNote(
    val absoluteNote: AbsoluteNote = AbsoluteNote.C3,
    val isSessionMainNote: Boolean = false,
)
