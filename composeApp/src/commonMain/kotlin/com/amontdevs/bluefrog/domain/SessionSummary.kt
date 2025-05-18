package com.amontdevs.bluefrog.domain

import com.amontdevs.bluefrog.domain.absolute.AbsoluteNote
import com.amontdevs.bluefrog.domain.absolute.AbsoluteNoteQuestion
import kotlin.time.Duration

data class SessionSummary(
    val correctAnswers: Int = 0,
    val totalQuestions: Int = 0,
    val sessionTime: Duration = Duration.ZERO,
    val sessionQuestions: List<AbsoluteNoteQuestion> = listOf(),
)

data class AbsoluteSessionSummaryQuestion(
    val absoluteNote: AbsoluteNote,
    val numberOfCorrectAnswers: Int = 0,
    val totalQuestions: Int = 0,
) {
    val successPercentage: Int
        get() = ((numberOfCorrectAnswers.toFloat() / totalQuestions.toFloat()) * 100).toInt()
    val answerLevel: SummaryAnswerLevel
        get() =
            if (successPercentage == 100) {
                SummaryAnswerLevel.PERFECT
            } else if (successPercentage >= 70) {
                SummaryAnswerLevel.GOOD
            } else if (successPercentage >= 50) {
                SummaryAnswerLevel.REGULAR
            } else {
                SummaryAnswerLevel.NEEDS_IMPROVEMENT
            }
}

enum class SummaryAnswerLevel {
    NEEDS_IMPROVEMENT,
    REGULAR,
    GOOD,
    PERFECT,
}
