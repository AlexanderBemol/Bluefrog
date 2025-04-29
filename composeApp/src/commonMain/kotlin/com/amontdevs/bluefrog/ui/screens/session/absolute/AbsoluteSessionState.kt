package com.amontdevs.bluefrog.ui.screens.session.absolute

import com.amontdevs.bluefrog.domain.AbsoluteNote
import kotlin.time.Duration

data class AbsoluteSessionState(
    val progress: Float = 0.0f,
    val questionIndex: Int = 0,
    val isSessionInProgress: Boolean = true,
    val absoluteQuestion: AbsoluteQuestion = AbsoluteQuestion.AbsoluteNoteState(),
    val sessionSummary: SessionSummaryState = SessionSummaryState()
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
    val correctAnswers: Int = 0,
    val totalQuestions: Int = 0,
    val sessionTime: Duration = Duration.ZERO,
    val answersSummary: List<AbsoluteSessionSummaryQuestion> = listOf()
)

data class AbsoluteSessionSummaryQuestion(
    val absoluteNote: AbsoluteNote,
    val numberOfCorrectAnswers: Int = 0,
    val totalQuestions: Int = 0,
) {
    val successPercentage: Int
        get() = ((numberOfCorrectAnswers.toFloat() / totalQuestions.toFloat()) * 100).toInt()
    val answerLevel: SummaryAnswerLevel
        get() = if (successPercentage == 100) SummaryAnswerLevel.PERFECT
                else if (successPercentage >= 70) SummaryAnswerLevel.GOOD
                else if (successPercentage >= 50) SummaryAnswerLevel.REGULAR
                else SummaryAnswerLevel.NEEDS_IMPROVEMENT
}

enum class SummaryAnswerLevel {
    NEEDS_IMPROVEMENT,
    REGULAR,
    GOOD,
    PERFECT
}
