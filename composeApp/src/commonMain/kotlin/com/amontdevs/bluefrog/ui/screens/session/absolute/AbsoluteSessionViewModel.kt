package com.amontdevs.bluefrog.ui.screens.session.absolute

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amontdevs.bluefrog.domain.AbsoluteSessionSummaryQuestion
import com.amontdevs.bluefrog.domain.BlueFrogResult
import com.amontdevs.bluefrog.domain.absolute.AbsoluteNote
import com.amontdevs.bluefrog.domain.absolute.AbsoluteNoteQuestion
import com.amontdevs.bluefrog.domain.absolute.getNoteToGuess
import com.amontdevs.bluefrog.domain.absolute.getUserGuess
import com.amontdevs.bluefrog.repository.IAbsoluteSessionRepository
import com.amontdevs.bluefrog.repository.IAudioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AbsoluteSessionViewModel(
    private val audioRepository: IAudioRepository,
    private val absoluteSessionRepository: IAbsoluteSessionRepository,
) : ViewModel() {
    private val _absoluteSessionState = MutableStateFlow(AbsoluteSessionState())
    val absoluteSessionState = _absoluteSessionState

    private val currentQuestion
        get() = _absoluteSessionState.value.absoluteQuestionState
    private var progressStepSize: Float = 0.0f

    init {
        generateSession()
        startSession()
    }

    private fun generateSession() {
        val result =
            absoluteSessionRepository.generateSession()
        when (result) {
            is BlueFrogResult.Success -> {
                progressStepSize = 1f / result.data
            }
            is BlueFrogResult.Error -> {
                // TODO: Log error and handle if needed
            }
        }
    }

    private fun startSession() {
        val result = absoluteSessionRepository.startSession()
        when (result) {
            is BlueFrogResult.Success -> {
                // update state with first question
                setCurrentQuestion(
                    progress = 0f,
                    question = result.data,
                )
            }
            is BlueFrogResult.Error -> {
                // TODO: Log error and handle if needed
            }
        }
    }

    fun playSound(absoluteNote: AbsoluteNote = currentQuestion.questionNote()) {
        viewModelScope.launch {
            audioRepository.playSound(absoluteNote)
        }
    }

    fun confirmGuess() {
        val progress = _absoluteSessionState.value.progress + progressStepSize
        val selectedNote =
            _absoluteSessionState.value.absoluteQuestionState
                .questionNotes()
                .first { it.optionState == OptionState.Selected }

        val confirmGuessResult = absoluteSessionRepository.checkAnswer(selectedNote.absoluteNote)
        val wasAnswerCorrect =
            when (confirmGuessResult) {
                is BlueFrogResult.Success -> {
                    confirmGuessResult.data
                }
                is BlueFrogResult.Error -> {
                    // TODO: Log error and handle if needed
                    false
                }
            }

        if (wasAnswerCorrect) {
            updateQuestionState(
                progress = progress,
                noteOptions =
                    currentQuestion.questionNotes().map { noteOption ->
                        noteOption.copy(
                            optionState =
                                if (noteOption.absoluteNote ==
                                    selectedNote.absoluteNote
                                ) {
                                    OptionState.SelectedAndCorrect
                                } else {
                                    OptionState.NotSelected
                                },
                        )
                    },
                answerState = AnswerState.Correct,
            )
        } else {
            updateQuestionState(
                progress = progress,
                noteOptions =
                    currentQuestion.questionNotes().map { noteOption ->
                        noteOption.copy(
                            optionState =
                                when (noteOption.absoluteNote) {
                                    selectedNote.absoluteNote -> OptionState.SelectedAndWrong
                                    currentQuestion.questionNote() -> OptionState.NotSelectedAndCorrect
                                    else -> OptionState.NotSelected
                                },
                        )
                    },
                answerState = AnswerState.Incorrect,
            )
        }
    }

    fun continueSession() {
        val continueSessionResult = absoluteSessionRepository.continueSession()
        val nextQuestion =
            when (continueSessionResult) {
                is BlueFrogResult.Success -> {
                    continueSessionResult.data
                }
                is BlueFrogResult.Error -> {
                    // TODO: Log error and handle if needed
                    null
                }
            }

        if (nextQuestion != null) {
            setCurrentQuestion(question = nextQuestion)
            if (currentQuestion is AbsoluteNoteQuestionState.GuessNoteNameState) {
                playSound(currentQuestion.questionNote())
            }
        } else {
            val finishSessionResult = absoluteSessionRepository.finishSession()
            val sessionSummary =
                when (finishSessionResult) {
                    is BlueFrogResult.Success -> {
                        finishSessionResult.data
                    }
                    is BlueFrogResult.Error -> {
                        // TODO: Log error and handle if needed
                        null
                    }
                }

            sessionSummary?.let { sessionSummary ->
                val sessionQuestionsSummary =
                    sessionSummary.sessionQuestions
                        .sortedBy { it.getNoteToGuess() }
                        .groupBy { question -> question.getNoteToGuess() }
                        .filter { (_, value) -> value.size >= 3 }
                        .map { questions ->
                            AbsoluteSessionSummaryQuestion(
                                absoluteNote = questions.key,
                                numberOfCorrectAnswers =
                                    questions.value.count {
                                        it.getNoteToGuess() == it.getUserGuess()
                                    },
                                totalQuestions = questions.value.size,
                            )
                        }

                _absoluteSessionState.value =
                    _absoluteSessionState.value.copy(
                        isSessionComplete = true,
                        sessionSummary =
                            SessionSummaryState(
                                correctAnswers = sessionSummary.correctAnswers,
                                totalQuestions = sessionSummary.totalQuestions,
                                sessionTime = sessionSummary.sessionTime,
                                answersSummary = sessionQuestionsSummary,
                            ),
                    )
            }
        }
    }

    fun selectOption(newSelectedOption: NoteOption) {
        val (answerState, options) =
            when (val question = _absoluteSessionState.value.absoluteQuestionState) {
                is AbsoluteNoteQuestionState.GuessNoteNameState ->
                    Pair(question.answerState, question.absoluteNotes)
                is AbsoluteNoteQuestionState.GuessNoteSoundState ->
                    Pair(question.answerState, question.absoluteNotes)
                is AbsoluteNoteQuestionState.LearningState ->
                    Pair(question.answerState, question.absoluteNotes)
            }

        if (answerState in setOf(AnswerState.NotAnsweredYet, AnswerState.NotSubmittedYet)) {
            val selectedNote = options.firstOrNull { it.optionState == OptionState.Selected }
            if (selectedNote != newSelectedOption) {
                updateQuestionState(
                    noteOptions =
                        currentQuestion.questionNotes().map { option ->
                            option.copy(
                                optionState =
                                    if (option.absoluteNote ==
                                        newSelectedOption.absoluteNote
                                    ) {
                                        OptionState.Selected
                                    } else {
                                        OptionState.NotSelected
                                    },
                            )
                        },
                    answerState = AnswerState.NotSubmittedYet,
                )
            }
        }
        // Play a sound when is this kind of question
        if (currentQuestion is AbsoluteNoteQuestionState.GuessNoteSoundState ||
            currentQuestion is AbsoluteNoteQuestionState.LearningState
        ) {
            playSound(newSelectedOption.absoluteNote)
        }
    }

    fun restartSession() {
        generateSession()
        startSession()
    }

    private fun setCurrentQuestion(
        progress: Float? = null,
        question: AbsoluteNoteQuestion,
    ) {
        val questionState =
            when (question) {
                is AbsoluteNoteQuestion.GuessNoteName ->
                    AbsoluteNoteQuestionState.GuessNoteNameState(
                        questionId = question.questionId,
                        noteToGuess = question.noteToGuess,
                        absoluteNotes = question.absoluteNotes.map { NoteOption(it) },
                    )
                is AbsoluteNoteQuestion.GuessNoteSound ->
                    AbsoluteNoteQuestionState.GuessNoteSoundState(
                        questionId = question.questionId,
                        noteToGuess = question.noteToGuess,
                        absoluteNotes = question.absoluteNotes.map { NoteOption(it) },
                    )
                is AbsoluteNoteQuestion.Learning ->
                    AbsoluteNoteQuestionState.LearningState(
                        questionId = question.questionId,
                        absoluteNotes = question.absoluteNotes.map { NoteOption(it) },
                    )
            }
        _absoluteSessionState.value =
            AbsoluteSessionState(
                progress = progress ?: _absoluteSessionState.value.progress,
                absoluteQuestionState = questionState,
            )
    }

    private fun updateQuestionState(
        progress: Float? = null,
        noteOptions: List<NoteOption>? = null,
        answerState: AnswerState? = null,
    ) {
        when (val question = _absoluteSessionState.value.absoluteQuestionState) {
            is AbsoluteNoteQuestionState.LearningState -> {
                _absoluteSessionState.value =
                    _absoluteSessionState.value.copy(
                        progress = progress ?: _absoluteSessionState.value.progress,
                    )
            }
            is AbsoluteNoteQuestionState.GuessNoteNameState -> {
                val newQuestion =
                    question.copy(
                        absoluteNotes = noteOptions ?: question.absoluteNotes,
                        answerState = answerState ?: question.answerState,
                    )
                _absoluteSessionState.value =
                    _absoluteSessionState.value.copy(
                        progress = progress ?: _absoluteSessionState.value.progress,
                        absoluteQuestionState = newQuestion,
                    )
            }
            is AbsoluteNoteQuestionState.GuessNoteSoundState -> {
                val newQuestion =
                    question.copy(
                        absoluteNotes = noteOptions ?: question.absoluteNotes,
                        answerState = answerState ?: question.answerState,
                    )
                _absoluteSessionState.value =
                    _absoluteSessionState.value.copy(
                        progress = progress ?: _absoluteSessionState.value.progress,
                        absoluteQuestionState = newQuestion,
                    )
            }
        }
    }
}
