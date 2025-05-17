package com.amontdevs.bluefrog.ui.screens.session.absolute

import com.amontdevs.bluefrog.domain.AbsoluteSessionSummaryQuestion
import com.amontdevs.bluefrog.domain.absolute.AbsoluteNote
import kotlin.time.Duration

data class AbsoluteSessionState(
    val progress: Float = 0.0f,
    val isSessionComplete: Boolean = false,
    val absoluteQuestionState: AbsoluteNoteQuestionState = AbsoluteNoteQuestionState.LearningState(),
    val sessionSummary: SessionSummaryState = SessionSummaryState(),
)

sealed class AbsoluteNoteQuestionState {
    data class LearningState(
        val questionId: Int = 0,
        val absoluteNotes: List<NoteOption> = listOf(),
        val answerState: AnswerState = AnswerState.NotAnsweredYet,
    ) : AbsoluteNoteQuestionState()

    data class GuessNoteNameState(
        val questionId: Int = 0,
        val noteToGuess: AbsoluteNote = AbsoluteNote.C3,
        val absoluteNotes: List<NoteOption> = listOf(),
        val answerState: AnswerState = AnswerState.NotAnsweredYet,
    ) : AbsoluteNoteQuestionState()

    data class GuessNoteSoundState(
        val questionId: Int = 0,
        val noteToGuess: AbsoluteNote = AbsoluteNote.C3,
        val absoluteNotes: List<NoteOption> = listOf(),
        val answerState: AnswerState = AnswerState.NotAnsweredYet,
    ) : AbsoluteNoteQuestionState()
}

data class NoteOption(
    val absoluteNote: AbsoluteNote = AbsoluteNote.C3,
    val optionState: OptionState = OptionState.NotSelected,
)

enum class OptionState {
    NotSelected,
    NotSelectedAndCorrect,
    Selected,
    SelectedAndCorrect,
    SelectedAndWrong,
}

enum class AnswerState {
    NotAnsweredYet,
    NotSubmittedYet,
    Correct,
    Incorrect,
}

fun AbsoluteNoteQuestionState.questionNotes() =
    when (this) {
        is AbsoluteNoteQuestionState.LearningState -> absoluteNotes
        is AbsoluteNoteQuestionState.GuessNoteNameState -> absoluteNotes
        is AbsoluteNoteQuestionState.GuessNoteSoundState -> absoluteNotes
    }

fun AbsoluteNoteQuestionState.questionNote() =
    when (this) {
        is AbsoluteNoteQuestionState.GuessNoteNameState -> noteToGuess
        is AbsoluteNoteQuestionState.GuessNoteSoundState -> noteToGuess
        is AbsoluteNoteQuestionState.LearningState -> throw UnsupportedOperationException()
    }

fun AbsoluteNoteQuestionState.questionId() =
    when (this) {
        is AbsoluteNoteQuestionState.GuessNoteNameState -> questionId
        is AbsoluteNoteQuestionState.GuessNoteSoundState -> questionId
        is AbsoluteNoteQuestionState.LearningState -> questionId
    }

data class SessionSummaryState(
    val correctAnswers: Int = 0,
    val totalQuestions: Int = 0,
    val sessionTime: Duration = Duration.ZERO,
    val answersSummary: List<AbsoluteSessionSummaryQuestion> = listOf(),
)
