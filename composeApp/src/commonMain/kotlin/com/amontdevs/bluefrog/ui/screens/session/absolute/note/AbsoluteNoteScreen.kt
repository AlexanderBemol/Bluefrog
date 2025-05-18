package com.amontdevs.bluefrog.ui.screens.session.absolute.note

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ic_check
import bluefrog.composeapp.generated.resources.ic_close
import bluefrog.composeapp.generated.resources.ic_play
import com.amontdevs.bluefrog.domain.absolute.AbsoluteNote
import com.amontdevs.bluefrog.ui.PrimaryIconButton
import com.amontdevs.bluefrog.ui.screens.session.AbsoluteNoteOptionCard
import com.amontdevs.bluefrog.ui.screens.session.AbsoluteQuestionHeader
import com.amontdevs.bluefrog.ui.screens.session.CheckButton
import com.amontdevs.bluefrog.ui.screens.session.absolute.AbsoluteNoteQuestionState
import com.amontdevs.bluefrog.ui.screens.session.absolute.AnswerState
import com.amontdevs.bluefrog.ui.screens.session.absolute.NoteOption
import com.amontdevs.bluefrog.ui.screens.session.absolute.OptionState
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AbsoluteNoteScreen(
    modifier: Modifier = Modifier,
    absoluteNoteState: AbsoluteNoteQuestionState.GuessNoteNameState,
    onPlayClick: () -> Unit,
    onOptionSelected: (selectedNote: NoteOption) -> Unit,
    onCheckClick: () -> Unit,
    onContinueClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AbsoluteQuestionHeader(
                modifier = Modifier.fillMaxWidth(),
                title = "¿Qué nota está sonando?",
                subtitle = "Selecciona la nota correcta.",
                answerState = absoluteNoteState.answerState,
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            AnimatedContent(
                targetState = absoluteNoteState.answerState,
                transitionSpec = {
                    if (initialState == AnswerState.NotAnsweredYet && targetState == AnswerState.NotSubmittedYet) {
                        EnterTransition.None togetherWith ExitTransition.None
                    } else {
                        slideInVertically(animationSpec = tween(durationMillis = 300)) { fullHeight -> fullHeight } + fadeIn() togetherWith
                            slideOutVertically(animationSpec = tween(durationMillis = 300)) { fullHeight -> -fullHeight } + fadeOut()
                    }
                },
                label = "Button State Animation",
            ) { answerState ->
                when (answerState) {
                    AnswerState.NotAnsweredYet, AnswerState.NotSubmittedYet -> {
                        PrimaryIconButton(
                            onClick = onPlayClick,
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_play),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(100.dp),
                            )
                        }
                    }
                    AnswerState.Correct -> {
                        PrimaryIconButton(
                            colors =
                                ButtonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary,
                                    contentColor = MaterialTheme.colorScheme.onSecondary,
                                    disabledContainerColor = MaterialTheme.colorScheme.outline,
                                    disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                                ),
                            onClick = onPlayClick,
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_check),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(100.dp),
                            )
                        }
                    }
                    AnswerState.Incorrect ->
                        PrimaryIconButton(
                            colors =
                                ButtonColors(
                                    containerColor = MaterialTheme.colorScheme.error,
                                    contentColor = MaterialTheme.colorScheme.onError,
                                    disabledContainerColor = MaterialTheme.colorScheme.outline,
                                    disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                                ),
                            onClick = onPlayClick,
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_close),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(100.dp),
                            )
                        }
                }
            }
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            absoluteNoteState.absoluteNotes.forEach { option ->
                AbsoluteNoteOptionCard(
                    modifier =
                        Modifier
                            .padding(4.dp),
                    noteOption = option,
                    onOptionSelected = {
                        onOptionSelected(option)
                    },
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            CheckButton(
                answerState = absoluteNoteState.answerState,
                onCheckClick = onCheckClick,
                onContinueClick = onContinueClick,
            )
        }
    }
}

@Composable
@Preview
fun AbsoluteNotePreview() {
    BlueFrogTheme {
        Scaffold { paddingValues ->
            Surface(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues)
                        .padding(16.dp),
            ) {
                AbsoluteNoteScreen(
                    absoluteNoteState =
                        AbsoluteNoteQuestionState.GuessNoteNameState(
                            answerState = AnswerState.NotSubmittedYet,
                            absoluteNotes =
                                listOf(
                                    NoteOption(AbsoluteNote.C3),
                                    NoteOption(AbsoluteNote.D3, OptionState.Selected),
                                    NoteOption(AbsoluteNote.E3),
                                ),
                        ),
                    onPlayClick = {},
                    onOptionSelected = {},
                    onCheckClick = {},
                    onContinueClick = {},
                )
            }
        }
    }
}
