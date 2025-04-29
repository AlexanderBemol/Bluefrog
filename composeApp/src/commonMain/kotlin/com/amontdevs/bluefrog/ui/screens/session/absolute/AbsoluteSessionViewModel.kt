package com.amontdevs.bluefrog.ui.screens.session.absolute

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amontdevs.bluefrog.domain.AbsoluteNote
import com.amontdevs.bluefrog.repository.IAudioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class AbsoluteSessionViewModel(
    private val audioRepository: IAudioRepository
): ViewModel() {

    private val _absoluteSessionState = MutableStateFlow(AbsoluteSessionState())
    val absoluteSessionState = _absoluteSessionState

    private var currentIndex = 0
    private val sessionQuestions = mutableListOf<AbsoluteQuestion>()
    private val currentQuestion
        get() = sessionQuestions[currentIndex]

    private var correctAnswersCount: Int = 0
    private var sessionStartInstant: Instant = Instant.fromEpochMilliseconds(0)

    private val availableNotes = listOf(
        AbsoluteNote.C3,
        AbsoluteNote.D3,
        AbsoluteNote.E3,
    )

    init {
        correctAnswersCount = 0
        generateSession(includeLearning = true)
        val initialProgress = 1 / sessionQuestions.size.toFloat()
        setCurrentQuestion(initialProgress)
        sessionStartInstant = Clock.System.now()
        //Play a sound when is this kind of question
        if (currentQuestion is AbsoluteQuestion.AbsoluteNoteState) {
            playSound()
        }
    }

    private fun generateSession(
        size: Int = 10,
        includeLearning: Boolean = false
    ) {
        val questionTypes = listOf(
            AbsoluteQuestion.AbsoluteNoteState::class,
            AbsoluteQuestion.AbsoluteSoundState::class
        )
        var lastAbsoluteNote: AbsoluteNote? = null
        sessionQuestions.clear()
        if (includeLearning) {
            //TODO: select the sounds to learn in the session
            val learningItem = AbsoluteQuestion.AbsoluteNotesLearningState(
                noteOptions = availableNotes.map { NoteOption(absoluteNote = it) }
            )
            sessionQuestions.add(learningItem)
        }
        repeat(size) {
            //TODO: select no more than 6 notes for the question
            val questionSelectedNotes = availableNotes.map { NoteOption(absoluteNote = it) }

            //fix to avoid same note one after the other
            val noteToGuess = if (lastAbsoluteNote == null) {
                questionSelectedNotes.random().absoluteNote
            } else {
                questionSelectedNotes.filter { it.absoluteNote != lastAbsoluteNote }.random().absoluteNote
            }

            val question = when(questionTypes.random()) {
                AbsoluteQuestion.AbsoluteNoteState::class -> AbsoluteQuestion.AbsoluteNoteState(
                    currentSound = noteToGuess,
                    noteOptions = questionSelectedNotes
                )
                AbsoluteQuestion.AbsoluteSoundState::class -> AbsoluteQuestion.AbsoluteSoundState(
                    currentSound = noteToGuess,
                    noteOptions = questionSelectedNotes.shuffled()
                )
                else -> { throw Exception("Unsupported type of question.") }
            }
            sessionQuestions.add(question)
            lastAbsoluteNote = noteToGuess
        }
    }

    private fun setCurrentQuestion(
        progress: Float? = null
    ) {
        _absoluteSessionState.value = AbsoluteSessionState(
            questionIndex = currentIndex,
            progress = progress ?: _absoluteSessionState.value.progress,
            absoluteQuestion = sessionQuestions[currentIndex]
        )
    }

    private fun AbsoluteQuestion.questionNote() = when(this) {
        is AbsoluteQuestion.AbsoluteNoteState -> currentSound
        is AbsoluteQuestion.AbsoluteSoundState -> currentSound
        else -> throw UnsupportedOperationException()
    }

    private fun AbsoluteQuestion.questionNotes() = when(this) {
        is AbsoluteQuestion.AbsoluteNotesLearningState -> noteOptions
        is AbsoluteQuestion.AbsoluteNoteState -> noteOptions
        is AbsoluteQuestion.AbsoluteSoundState -> noteOptions
    }

    fun playSound(
        absoluteNote: AbsoluteNote = currentQuestion.questionNote()
    ) {
        viewModelScope.launch {
            audioRepository.playSound(absoluteNote)
        }
    }

    fun confirmGuess() {
        val progress = (currentIndex + 1) / sessionQuestions.size.toFloat()
        val selectedNote = when(val question = _absoluteSessionState.value.absoluteQuestion) {
            is AbsoluteQuestion.AbsoluteNoteState -> question.noteOptions.first { it.optionState == OptionState.Selected }
            is AbsoluteQuestion.AbsoluteSoundState -> question.noteOptions.first { it.optionState == OptionState.Selected }
            is AbsoluteQuestion.AbsoluteNotesLearningState -> throw UnsupportedOperationException()
        }
        //If the guess was correct
        if (selectedNote.absoluteNote == currentQuestion.questionNote()) {
            updateQuestionState(
                progress = progress,
                noteOptions = currentQuestion.questionNotes().map { noteOption ->
                    noteOption.copy(
                        optionState = if(noteOption.absoluteNote == selectedNote.absoluteNote) OptionState.SelectedAndCorrect else OptionState.NotSelected
                    )
                },
                answerState = AnswerState.Correct
            )
            correctAnswersCount ++
        } else {
            updateQuestionState(
                progress = progress,
                noteOptions = currentQuestion.questionNotes().map { noteOption ->
                    noteOption.copy(
                        optionState = when (noteOption.absoluteNote) {
                            selectedNote.absoluteNote -> OptionState.SelectedAndWrong
                            currentQuestion.questionNote() -> OptionState.NotSelectedAndCorrect
                            else -> OptionState.NotSelected
                        }
                    )
                },
                answerState = AnswerState.Incorrect
            )
        }
    }

    fun continueSession() {
        if (_absoluteSessionState.value.progress < 1f) {
            currentIndex++
            setCurrentQuestion()
            //Play a sound when is this kind of question
            if (currentQuestion is AbsoluteQuestion.AbsoluteNoteState) {
                playSound()
            }
        } else {
            //calculate score
            val sessionTime = Clock.System.now() - sessionStartInstant

            //Calculate percentage of success for each note with at least 3 occurrences
            val validQuestions = sessionQuestions.filter { it !is AbsoluteQuestion.AbsoluteNotesLearningState }
            val sessionQuestionsSummary = validQuestions
                .sortedBy { it.questionNote() }
                .groupBy { question -> question.questionNote() }
                .filter { (_, value) -> value.size >= 3 }
                .map { questions ->
                    AbsoluteSessionSummaryQuestion(
                        absoluteNote = questions.key,
                        numberOfCorrectAnswers = questions.value.count {
                            it.questionNotes().any { option -> option.optionState == OptionState.SelectedAndCorrect } },
                        totalQuestions = questions.value.size,
                    )
                }

            _absoluteSessionState.value = _absoluteSessionState.value.copy(
                isSessionInProgress = false,
                sessionSummary = SessionSummaryState(
                    correctAnswers = correctAnswersCount,
                    totalQuestions = validQuestions.size,
                    sessionTime = sessionTime,
                    answersSummary = sessionQuestionsSummary
                ),
            )
        }
    }

    fun selectOption(newSelectedOption: NoteOption) {
        val (answerState, options) = when (val question = _absoluteSessionState.value.absoluteQuestion) {
            is AbsoluteQuestion.AbsoluteNoteState -> Pair(question.answerState, question.noteOptions)
            is AbsoluteQuestion.AbsoluteSoundState -> Pair(question.answerState, question.noteOptions)
            is AbsoluteQuestion.AbsoluteNotesLearningState -> Pair(question.answerState, question.noteOptions)
        }

        if (answerState in setOf(AnswerState.NotAnsweredYet, AnswerState.NotSubmittedYet)) {
            val selectedNote = options.firstOrNull { it.optionState == OptionState.Selected }
            if(selectedNote != newSelectedOption) {
                updateQuestionState(
                    noteOptions = currentQuestion.questionNotes().map { option ->
                        option.copy(
                            optionState = if(option.absoluteNote == newSelectedOption.absoluteNote) OptionState.Selected else OptionState.NotSelected
                        )
                    },
                    answerState = AnswerState.NotSubmittedYet
                )
            }
        }
        //Play a sound when is this kind of question
        if (currentQuestion is AbsoluteQuestion.AbsoluteSoundState ||
            currentQuestion is AbsoluteQuestion.AbsoluteNotesLearningState) {
            playSound(newSelectedOption.absoluteNote)
        }
    }

    fun restartSession() {
        correctAnswersCount = 0
        currentIndex = 0
        generateSession()
        setCurrentQuestion(
            progress = 0f
        )
        sessionStartInstant = Clock.System.now()
        //Play a sound when is this kind of question
        if (currentQuestion is AbsoluteQuestion.AbsoluteNoteState) {
            playSound()
        }
    }

    private fun updateQuestionState(
        progress: Float? = null,
        noteOptions: List<NoteOption>? = null,
        answerState: AnswerState? = null
    ) {
        when (val question = _absoluteSessionState.value.absoluteQuestion) {
            is AbsoluteQuestion.AbsoluteNotesLearningState -> {
                _absoluteSessionState.value = _absoluteSessionState.value.copy(
                    progress = progress ?: _absoluteSessionState.value.progress
                )
            }
            is AbsoluteQuestion.AbsoluteNoteState -> {
                val newQuestion = question.copy(
                    noteOptions = noteOptions ?: question.noteOptions,
                    answerState = answerState ?: question.answerState
                )
                _absoluteSessionState.value = _absoluteSessionState.value.copy(
                    progress = progress ?: _absoluteSessionState.value.progress,
                    absoluteQuestion = newQuestion
                )
                sessionQuestions[currentIndex] = newQuestion
            }
            is AbsoluteQuestion.AbsoluteSoundState -> {
                val newQuestion = question.copy(
                    noteOptions = noteOptions ?: question.noteOptions,
                    answerState = answerState ?: question.answerState
                )
                _absoluteSessionState.value = _absoluteSessionState.value.copy(
                    progress = progress ?: _absoluteSessionState.value.progress,
                    absoluteQuestion = newQuestion
                )
                sessionQuestions[currentIndex] = newQuestion
            }
        }
    }


}