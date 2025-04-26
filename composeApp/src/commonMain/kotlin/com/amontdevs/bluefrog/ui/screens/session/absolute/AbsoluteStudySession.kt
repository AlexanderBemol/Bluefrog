package com.amontdevs.bluefrog.ui.screens.session.absolute

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amontdevs.bluefrog.domain.AbsoluteNote
import com.amontdevs.bluefrog.ui.screens.session.absolute.learning.LearningNotesScreen
import com.amontdevs.bluefrog.ui.screens.session.absolute.note.AbsoluteNoteScreen
import com.amontdevs.bluefrog.ui.screens.session.absolute.sound.AbsoluteSoundScreen
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudySession(
    absoluteSessionViewModel: AbsoluteSessionViewModel = koinInject()
) {
    val absoluteSessionStateFlow = absoluteSessionViewModel.absoluteSessionState
    AbsoluteStudySession(
        absoluteSessionStateFlow = absoluteSessionStateFlow,
        onPlayClick = {
            absoluteSessionViewModel.playSound()
        },
        onOptionSelected = {
            absoluteSessionViewModel.selectOption(it)
        },
        onCheckClick = {
            absoluteSessionViewModel.confirmGuess()
        },
        onContinueClick = {
            absoluteSessionViewModel.continueSession()
        },
        onRestartSession = {
            absoluteSessionViewModel.restartSession()
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AbsoluteStudySession(
    absoluteSessionStateFlow: StateFlow<AbsoluteSessionState>,
    onPlayClick: () -> Unit,
    onOptionSelected: (NoteOption) -> Unit,
    onCheckClick: () -> Unit,
    onContinueClick: () -> Unit,
    onRestartSession: () -> Unit
) {
    val absoluteSessionState = absoluteSessionStateFlow.collectAsState()
    val animatedProgress by animateFloatAsState(
        targetValue = absoluteSessionState.value.progress,
        animationSpec = tween(durationMillis = 300, easing = EaseInOut),
        label = "Progress Bar Animation"
    )

    Column {
        AnimatedVisibility(absoluteSessionState.value.isSessionInProgress){
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier.fillMaxWidth().height(7.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primaryContainer
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(
                    vertical =  8.dp,
                    horizontal = 8.dp
                ),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        if (absoluteSessionState.value.isSessionInProgress) {
            AnimatedContent(
                targetState = absoluteSessionState.value,
                transitionSpec = {
                    val tweenDuration = 150
                    // New content slides in from the right
                    val enter = slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }, // Start full width to the right
                        animationSpec = tween(durationMillis = tweenDuration, easing = LinearEasing) // Adjust timing/easing
                    ) + fadeIn(animationSpec = tween(100)) // Optional quick fade in

                    // Old content slides out to the left
                    val exit = slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }, // End full width to the left
                        animationSpec = tween(durationMillis = tweenDuration, easing = LinearEasing) // Adjust timing/easing
                    ) + fadeOut(animationSpec = tween(100)) // Optional quick fade out

                    // Combine enter and exit transitions
                    enter togetherWith exit
                },
                contentKey = {state -> state.questionIndex}
            ) { targetState ->
                when (val question = targetState.absoluteQuestion) {
                    is AbsoluteQuestion.AbsoluteNotesLearningState -> {
                        LearningNotesScreen(
                            modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp,
                                    top = 0.dp
                                ),
                            absoluteNotesLearningState = question,
                            onOptionSelected = onOptionSelected,
                            onContinueClick = onContinueClick
                        )
                    }
                    is AbsoluteQuestion.AbsoluteNoteState -> {
                        AbsoluteNoteScreen(
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp,
                                    top = 0.dp
                                ),
                            absoluteNoteState = question,
                            onPlayClick = onPlayClick,
                            onOptionSelected = onOptionSelected,
                            onCheckClick = onCheckClick,
                            onContinueClick = onContinueClick
                        )
                    }
                    is AbsoluteQuestion.AbsoluteSoundState -> {
                        AbsoluteSoundScreen(
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp,
                                    top = 0.dp
                                ),
                            absoluteSoundState = question,
                            onOptionSelected = onOptionSelected,
                            onCheckClick = onCheckClick,
                            onContinueClicked = onContinueClick
                        )
                    }
                }
            }
        } else {
            SessionSummary(
                modifier = Modifier.padding(16.dp),
                summaryState = SessionSummaryState(absoluteSessionState.value.sessionScore),
                onContinueClick = onRestartSession
            )
        }


    }
}

@Preview
@Composable
fun PreviewStudySession() {
    BlueFrogTheme(
        darkTheme = false
    ) {
        Scaffold { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
            ){
                AbsoluteStudySession(
                    absoluteSessionStateFlow = MutableStateFlow(
                        AbsoluteSessionState(
                            progress = .35f,
                            absoluteQuestion = AbsoluteQuestion.AbsoluteNoteState(
                                noteOptions = listOf(
                                    NoteOption(AbsoluteNote.C3, optionState = OptionState.NotSelected),
                                    NoteOption(AbsoluteNote.D3, optionState = OptionState.Selected),
                                    NoteOption(AbsoluteNote.E3),
                                )
                            )
                        )
                    ),
                    onPlayClick = { },
                    onOptionSelected = { },
                    onCheckClick = { },
                    onContinueClick = { },
                    onRestartSession = { }
                )
            }
        }
    }
}