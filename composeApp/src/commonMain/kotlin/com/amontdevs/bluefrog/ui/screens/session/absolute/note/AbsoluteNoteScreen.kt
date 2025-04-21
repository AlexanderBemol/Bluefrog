package com.amontdevs.bluefrog.ui.screens.session.absolute.note

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.amontdevs.bluefrog.domain.AbsoluteNote
import com.amontdevs.bluefrog.ui.PrimaryIconButton
import com.amontdevs.bluefrog.ui.animatePlacement
import com.amontdevs.bluefrog.ui.screens.session.AbsoluteQuestionHeader
import com.amontdevs.bluefrog.ui.screens.session.CheckButton
import com.amontdevs.bluefrog.ui.screens.session.absolute.AbsoluteQuestion
import com.amontdevs.bluefrog.ui.screens.session.absolute.AnswerState
import com.amontdevs.bluefrog.ui.screens.session.absolute.NoteOption
import com.amontdevs.bluefrog.ui.screens.session.absolute.OptionState
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AbsoluteNoteScreen(
    modifier: Modifier = Modifier,
    absoluteNoteState: AbsoluteQuestion.AbsoluteNoteState,
    onPlayClick: () -> Unit,
    onOptionSelected: (selectedNote: NoteOption) -> Unit,
    onCheckClick: () -> Unit,
    onContinueClick: () -> Unit
){
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AbsoluteQuestionHeader(
                modifier = Modifier.fillMaxWidth(),
                title = "¿Qué nota está sonando?",
                subtitle = "Selecciona la nota correcta.",
                answerState = absoluteNoteState.answerState
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
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
                label = "Button State Animation"
            ){ answerState ->
                when(answerState) {
                    AnswerState.NotAnsweredYet, AnswerState.NotSubmittedYet -> {
                        PrimaryIconButton(
                            onClick = onPlayClick
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PlayArrow,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }
                    AnswerState.Correct -> {
                        PrimaryIconButton(
                            colors = ButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary,
                                disabledContainerColor = MaterialTheme.colorScheme.outline,
                                disabledContentColor = MaterialTheme.colorScheme.onSecondary
                            ),
                            onClick = onPlayClick
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }
                    AnswerState.Incorrect -> PrimaryIconButton(
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError,
                            disabledContainerColor = MaterialTheme.colorScheme.outline,
                            disabledContentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        onClick = onPlayClick
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            }

        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            absoluteNoteState.noteOptions.forEach { option ->
                OptionCard(
                    modifier = Modifier
                        .padding(4.dp),
                    noteOption = option,
                    onOptionSelected = {
                        onOptionSelected(option)
                    }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CheckButton(
                answerState = absoluteNoteState.answerState,
                onCheckClick = onCheckClick,
                onContinueClick = onContinueClick
            )
        }
    }
}

@Composable
fun OptionCard(
    modifier: Modifier = Modifier,
    noteOption: NoteOption = NoteOption(),
    onOptionSelected:()-> Unit = {}
) {
    val borderColor = when(noteOption.optionState) {
        OptionState.NotSelected -> MaterialTheme.colorScheme.outline
        OptionState.NotSelectedAndCorrect, OptionState.SelectedAndCorrect -> MaterialTheme.colorScheme.onSecondary
        OptionState.Selected -> MaterialTheme.colorScheme.inversePrimary
        OptionState.SelectedAndWrong -> MaterialTheme.colorScheme.onError
    }

    val backgroundColor = when(noteOption.optionState) {
        OptionState.NotSelected -> MaterialTheme.colorScheme.background
        OptionState.NotSelectedAndCorrect, OptionState.SelectedAndCorrect -> MaterialTheme.colorScheme.secondaryContainer
        OptionState.Selected -> MaterialTheme.colorScheme.primaryContainer
        OptionState.SelectedAndWrong -> MaterialTheme.colorScheme.errorContainer
    }

    val contentColor = when(noteOption.optionState) {
        OptionState.NotSelected -> MaterialTheme.colorScheme.onBackground
        OptionState.NotSelectedAndCorrect, OptionState.SelectedAndCorrect -> MaterialTheme.colorScheme.onSecondaryContainer
        OptionState.Selected -> MaterialTheme.colorScheme.onPrimaryContainer
        OptionState.SelectedAndWrong -> MaterialTheme.colorScheme.onErrorContainer
    }

    OutlinedCard(
        modifier = modifier,
        border = BorderStroke(
            1.dp,
            borderColor
        ),
        onClick = {
            onOptionSelected()
        },
        colors = CardDefaults.cardColors().copy(
            containerColor = backgroundColor
        )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ){
            Icon(
                painter =
                    painterResource(noteOption.absoluteNote.drawableResource),
                modifier = Modifier
                    .width(80.dp),
                contentDescription = "",
                tint = contentColor
            )
            Text(
                text = noteOption.absoluteNote.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = contentColor
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .padding(16.dp)
            ){
                AbsoluteNoteScreen(
                    absoluteNoteState = AbsoluteQuestion.AbsoluteNoteState(
                        answerState = AnswerState.NotSubmittedYet,
                        noteOptions = listOf(
                            NoteOption(AbsoluteNote.C3),
                            NoteOption(AbsoluteNote.D3, OptionState.Selected),
                            NoteOption(AbsoluteNote.E3)
                        )
                    ),
                    onPlayClick = {},
                    onOptionSelected = {},
                    onCheckClick = {},
                    onContinueClick = {}
                )
            }
        }
    }
}