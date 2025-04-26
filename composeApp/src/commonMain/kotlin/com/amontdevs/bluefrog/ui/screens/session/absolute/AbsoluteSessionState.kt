package com.amontdevs.bluefrog.ui.screens.session.absolute

import com.amontdevs.bluefrog.domain.AbsoluteNote

data class AbsoluteSessionState(
    val progress: Float = 0.0f,
    val questionIndex: Int = 0,
    val isSessionInProgress: Boolean = true,
    val sessionScore: Float = 0.0f,
    val absoluteQuestion: AbsoluteQuestion = AbsoluteQuestion.AbsoluteNoteState()
)

sealed class AbsoluteQuestion {

    data class AbsoluteNotesLearningState(
        val answerState: AnswerState = AnswerState.Correct,
        val noteOptions: List<NoteOption> = listOf()
    ) : AbsoluteQuestion()

    data class AbsoluteNoteState(
        val currentSound: AbsoluteNote = AbsoluteNote.C3,
        val answerState: AnswerState = AnswerState.NotAnsweredYet,
        val noteOptions: List<NoteOption> = listOf()
    ) : AbsoluteQuestion()

    data class AbsoluteSoundState(
        val currentSound: AbsoluteNote = AbsoluteNote.C3,
        val answerState: AnswerState = AnswerState.NotAnsweredYet,
        val noteOptions: List<NoteOption> = listOf()
    ) : AbsoluteQuestion()
}

data class NoteOption(
    val absoluteNote: AbsoluteNote = AbsoluteNote.C3,
    val optionState: OptionState = OptionState.NotSelected
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
    Incorrect
}

data class SessionSummaryState(
    val score: Float = 0f
)


