package com.amontdevs.bluefrog.ui.screens.home.customsession

import com.amontdevs.bluefrog.domain.absolute.AbsoluteNote

data class CustomSessionState(
    val title: String,
    val sessionNotes: List<CustomSessionAbsoluteNoteItem>,
)

data class CustomSessionAbsoluteNoteItem(
    val note: AbsoluteNote = AbsoluteNote.C3,
    var isSelected: Boolean = false,
)
