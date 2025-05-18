package com.amontdevs.bluefrog.domain.absolute

sealed class AbsoluteNoteQuestion {
    data class Learning(
        val questionId: Int = 0,
        val absoluteNotes: List<AbsoluteNote> = listOf(),
    ) : AbsoluteNoteQuestion()

    data class GuessNoteName(
        val questionId: Int = 0,
        val noteToGuess: AbsoluteNote = AbsoluteNote.C3,
        val absoluteNotes: List<AbsoluteNote> = listOf(),
        val userGuess: AbsoluteNote? = null,
    ) : AbsoluteNoteQuestion()

    data class GuessNoteSound(
        val questionId: Int = 0,
        val noteToGuess: AbsoluteNote = AbsoluteNote.C3,
        val absoluteNotes: List<AbsoluteNote> = listOf(),
        val userGuess: AbsoluteNote? = null,
    ) : AbsoluteNoteQuestion()
}

fun AbsoluteNoteQuestion.getNoteToGuess() =
    when (this) {
        is AbsoluteNoteQuestion.GuessNoteSound -> noteToGuess
        is AbsoluteNoteQuestion.GuessNoteName -> noteToGuess
        else -> throw UnsupportedOperationException()
    }

fun AbsoluteNoteQuestion.getUserGuess() =
    when (this) {
        is AbsoluteNoteQuestion.GuessNoteSound -> userGuess
        is AbsoluteNoteQuestion.GuessNoteName -> userGuess
        else -> throw UnsupportedOperationException()
    }
