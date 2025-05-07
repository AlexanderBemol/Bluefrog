package com.amontdevs.bluefrog.ui.screens.session.absolute.sound

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ic_bars
import com.amontdevs.bluefrog.domain.AbsoluteNote
import com.amontdevs.bluefrog.ui.animatePlacement
import com.amontdevs.bluefrog.ui.screens.session.AbsoluteQuestionHeader
import com.amontdevs.bluefrog.ui.screens.session.CheckButton
import com.amontdevs.bluefrog.ui.screens.session.absolute.AbsoluteQuestion
import com.amontdevs.bluefrog.ui.screens.session.absolute.AnswerState
import com.amontdevs.bluefrog.ui.screens.session.absolute.NoteOption
import com.amontdevs.bluefrog.ui.screens.session.absolute.OptionState
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AbsoluteSoundScreen(
    modifier: Modifier = Modifier,
    absoluteSoundState: AbsoluteQuestion.AbsoluteSoundState,
    onOptionSelected: (NoteOption) -> Unit,
    onCheckClick: () -> Unit,
    onContinueClicked: () -> Unit,
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
                title = "¿Cómo suena ésta nota?",
                subtitle = "Selecciona el sonido correcto para la nota mostrada.",
                answerState = absoluteSoundState.answerState,
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val contentColor =
                    when (absoluteSoundState.answerState) {
                        AnswerState.NotAnsweredYet, AnswerState.NotSubmittedYet -> MaterialTheme.colorScheme.onBackground
                        AnswerState.Correct -> MaterialTheme.colorScheme.secondary
                        AnswerState.Incorrect -> MaterialTheme.colorScheme.error
                    }
                val animatedColor by animateColorAsState(contentColor)

                Icon(
                    painter = painterResource(absoluteSoundState.currentSound.drawableResource),
                    contentDescription = "",
                    modifier = Modifier.size(140.dp),
                    tint = animatedColor,
                )
                Text(
                    text = absoluteSoundState.currentSound.toString(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = animatedColor,
                )
            }
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            absoluteSoundState.noteOptions.forEach { option ->
                SoundOptionCard(
                    modifier =
                        Modifier
                            .padding(4.dp)
                            .animatePlacement(),
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
                answerState = absoluteSoundState.answerState,
                onCheckClick = onCheckClick,
                onContinueClick = onContinueClicked,
            )
        }
    }
}

@Composable
fun SoundOptionCard(
    modifier: Modifier = Modifier,
    noteOption: NoteOption = NoteOption(),
    onOptionSelected: () -> Unit = {},
) {
    val borderColor =
        when (noteOption.optionState) {
            OptionState.NotSelected -> MaterialTheme.colorScheme.outline
            OptionState.NotSelectedAndCorrect, OptionState.SelectedAndCorrect -> MaterialTheme.colorScheme.onSecondary
            OptionState.Selected -> MaterialTheme.colorScheme.inversePrimary
            OptionState.SelectedAndWrong -> MaterialTheme.colorScheme.onError
        }

    val backgroundColor =
        when (noteOption.optionState) {
            OptionState.NotSelected -> MaterialTheme.colorScheme.background
            OptionState.NotSelectedAndCorrect, OptionState.SelectedAndCorrect -> MaterialTheme.colorScheme.secondaryContainer
            OptionState.Selected -> MaterialTheme.colorScheme.primaryContainer
            OptionState.SelectedAndWrong -> MaterialTheme.colorScheme.errorContainer
        }

    val contentColor =
        when (noteOption.optionState) {
            OptionState.NotSelected -> MaterialTheme.colorScheme.onBackground
            OptionState.NotSelectedAndCorrect, OptionState.SelectedAndCorrect -> MaterialTheme.colorScheme.onSecondaryContainer
            OptionState.Selected -> MaterialTheme.colorScheme.onPrimaryContainer
            OptionState.SelectedAndWrong -> MaterialTheme.colorScheme.onErrorContainer
        }

    OutlinedCard(
        modifier = modifier,
        border =
            BorderStroke(
                1.dp,
                borderColor,
            ),
        onClick = {
            onOptionSelected()
        },
        colors =
            CardDefaults.cardColors().copy(
                containerColor = backgroundColor,
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(
                        vertical = 64.dp,
                        horizontal = 16.dp,
                    ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Icon(
                painter =
                    painterResource(Res.drawable.ic_bars),
                modifier =
                    Modifier
                        .width(80.dp),
                contentDescription = "",
                tint = contentColor,
            )
        }
    }
}

@Composable
@Preview
fun AbsoluteSoundScreenPreview() {
    BlueFrogTheme {
        Scaffold { paddingValues ->
            Surface(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues)
                        .padding(16.dp),
            ) {
                AbsoluteSoundScreen(
                    absoluteSoundState =
                        AbsoluteQuestion.AbsoluteSoundState(
                            currentSound = AbsoluteNote.D3,
                            noteOptions =
                                listOf(
                                    NoteOption(AbsoluteNote.C3),
                                    NoteOption(AbsoluteNote.D3, OptionState.Selected),
                                    NoteOption(AbsoluteNote.E3),
                                ),
                            answerState = AnswerState.NotSubmittedYet,
                        ),
                    onOptionSelected = {},
                    onCheckClick = {},
                    onContinueClicked = {},
                )
            }
        }
    }
}
