package com.amontdevs.bluefrog.repository

import com.amontdevs.bluefrog.domain.BlueFrogResult
import com.amontdevs.bluefrog.domain.SessionSummary
import com.amontdevs.bluefrog.domain.absolute.AbsoluteNote
import com.amontdevs.bluefrog.domain.absolute.AbsoluteNoteQuestion
import com.amontdevs.bluefrog.domain.absolute.CustomSession
import com.amontdevs.bluefrog.domain.absolute.PredefinedAbsoluteSessions
import com.amontdevs.bluefrog.domain.absolute.getNoteToGuess
import com.amontdevs.bluefrog.domain.absolute.getUserGuess
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

interface IAbsoluteSessionRepository {
    fun generateSession(): BlueFrogResult<Int>

    fun startSession(): BlueFrogResult<AbsoluteNoteQuestion>

    fun checkAnswer(absoluteNote: AbsoluteNote): BlueFrogResult<Boolean>

    fun continueSession(): BlueFrogResult<AbsoluteNoteQuestion?>

    fun finishSession(): BlueFrogResult<SessionSummary>
}

class AbsoluteSessionRepository(
    customSessionId: Int,
) : IAbsoluteSessionRepository {
    private val sessionQuestions = mutableListOf<AbsoluteNoteQuestion>()
    private var currentIndex: Int = 0
    private val currentQuestion
        get() = sessionQuestions[currentIndex]

    private var sessionStartInstant: Instant = Instant.fromEpochMilliseconds(0)
    private var customSession: CustomSession =
        PredefinedAbsoluteSessions.entries
            .first { it.customSession.id == customSessionId }
            .customSession

    override fun generateSession(): BlueFrogResult<Int> =
        try {
            val sessionSize = 10
            val questionTypes =
                listOf(
                    AbsoluteNoteQuestion.GuessNoteName::class,
                    AbsoluteNoteQuestion.GuessNoteSound::class,
                )
            var lastAbsoluteNote: AbsoluteNote? = null

            if (customSession.isPredefined) {
                val mainNotes =
                    PredefinedAbsoluteSessions.entries
                        .first { it.customSession.predefinedId == customSession.predefinedId }
                        .customSession.notes

                sessionQuestions.add(
                    AbsoluteNoteQuestion.Learning(
                        questionId = 0,
                        absoluteNotes = mainNotes,
                    ),
                )
            }

            var questionId = 1
            repeat(sessionSize) {
                val questionSelectedNotes =
                    customSession.notes
                        .map { it.absoluteNote }
                        .shuffled()
                        .take(3)

                val noteToGuess =
                    if (lastAbsoluteNote == null) {
                        questionSelectedNotes.random()
                    } else {
                        questionSelectedNotes.filter { it != lastAbsoluteNote }.random()
                    }

                val question =
                    when (questionTypes.random()) {
                        AbsoluteNoteQuestion.GuessNoteName::class ->
                            AbsoluteNoteQuestion.GuessNoteName(
                                questionId = questionId,
                                noteToGuess = noteToGuess,
                                absoluteNotes = questionSelectedNotes.shuffled(),
                            )
                        AbsoluteNoteQuestion.GuessNoteSound::class ->
                            AbsoluteNoteQuestion.GuessNoteSound(
                                questionId = questionId,
                                noteToGuess = noteToGuess,
                                absoluteNotes = questionSelectedNotes.shuffled(),
                            )
                        else -> {
                            throw Exception("Unsupported type of question.")
                        }
                    }
                sessionQuestions.add(question)
                lastAbsoluteNote = noteToGuess
                questionId++
            }
            BlueFrogResult.Success(sessionQuestions.size)
        } catch (e: Exception) {
            BlueFrogResult.Error(e)
        }

    override fun startSession(): BlueFrogResult<AbsoluteNoteQuestion> =
        try {
            sessionStartInstant = Clock.System.now()
            BlueFrogResult.Success(currentQuestion)
        } catch (e: Exception) {
            BlueFrogResult.Error(e)
        }

    override fun checkAnswer(absoluteNote: AbsoluteNote): BlueFrogResult<Boolean> =
        try {
            val answeredQuestion =
                when (val question = currentQuestion) {
                    is AbsoluteNoteQuestion.GuessNoteName -> question.copy(userGuess = absoluteNote)
                    is AbsoluteNoteQuestion.GuessNoteSound -> question.copy(userGuess = absoluteNote)
                    else -> throw UnsupportedOperationException()
                }
            sessionQuestions[currentIndex] = answeredQuestion
            BlueFrogResult.Success(currentQuestion.getNoteToGuess() == absoluteNote)
        } catch (e: Exception) {
            BlueFrogResult.Error(e)
        }

    override fun continueSession(): BlueFrogResult<AbsoluteNoteQuestion?> =
        try {
            if (currentIndex < sessionQuestions.size - 1) {
                currentIndex++
                BlueFrogResult.Success(currentQuestion)
            } else {
                BlueFrogResult.Success(null)
            }
        } catch (e: Exception) {
            BlueFrogResult.Error(e)
        }

    override fun finishSession(): BlueFrogResult<SessionSummary> =
        try {
            val sessionTime = Clock.System.now() - sessionStartInstant
            val validQuestions = sessionQuestions.filter { it !is AbsoluteNoteQuestion.Learning }
            BlueFrogResult.Success(
                SessionSummary(
                    correctAnswers = validQuestions.count { it.getNoteToGuess() == it.getUserGuess() },
                    totalQuestions = validQuestions.size,
                    sessionTime = sessionTime,
                    sessionQuestions = validQuestions,
                ),
            )
        } catch (e: Exception) {
            BlueFrogResult.Error(e)
        } finally {
            currentIndex = 0
            sessionQuestions.clear()
        }
}
