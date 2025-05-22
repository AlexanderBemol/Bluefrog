package com.amontdevs.bluefrog.ui.screens.home.customsession

import androidx.lifecycle.ViewModel
import com.amontdevs.bluefrog.domain.absolute.AbsoluteNote
import kotlinx.coroutines.flow.MutableStateFlow

class CustomSessionViewModel : ViewModel() {
    private val _customSessionState = MutableStateFlow(CustomSessionState(title = "", sessionNotes = emptyList()))
    val customSessionState = _customSessionState

    init {
        _customSessionState.value =
            CustomSessionState(
                title = "",
                sessionNotes =
                    AbsoluteNote.entries
                        .map { CustomSessionAbsoluteNoteItem(note = it) }
                        .sortedBy { it.note.ordinal },
            )
    }

    fun onCheckAbsoluteNoteItem(absoluteNote: AbsoluteNote) {
        val currentItemsList = _customSessionState.value.sessionNotes
        val newItem =
            currentItemsList
                .filter { it.note == absoluteNote }
                .map { CustomSessionAbsoluteNoteItem(note = it.note, isSelected = !it.isSelected) }

        _customSessionState.value =
            _customSessionState.value.copy(
                sessionNotes =
                    (currentItemsList.filter { it.note != absoluteNote } + newItem)
                        .sortedBy { it.note.ordinal },
            )
    }

    fun onTitleTextFieldValueChanged(text: String) {
        _customSessionState.value = _customSessionState.value.copy(title = text)
    }
}
