package com.amontdevs.bluefrog.ui.screens.session

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ic_check
import com.amontdevs.bluefrog.ui.screens.session.absolute.AnswerState
import com.amontdevs.bluefrog.ui.screens.session.absolute.NoteOption
import com.amontdevs.bluefrog.ui.screens.session.absolute.OptionState
import org.jetbrains.compose.resources.painterResource

@Composable
fun CheckButton(
    answerState: AnswerState,
    onCheckClick: () -> Unit,
    onContinueClick: () -> Unit,
) {
    val isNotAnswered = answerState in setOf(AnswerState.NotAnsweredYet, AnswerState.NotSubmittedYet)
    Button(
        enabled = isNotAnswered.not() || answerState == AnswerState.NotSubmittedYet,
        modifier = Modifier.fillMaxWidth(),
        onClick = if (isNotAnswered) onCheckClick else onContinueClick,
        colors =
            if (isNotAnswered) {
                ButtonDefaults.buttonColors()
            } else {
                ButtonDefaults.buttonColors(
                    containerColor =
                        if (answerState ==
                            AnswerState.Correct
                        ) {
                            MaterialTheme.colorScheme.secondary
                        } else {
                            MaterialTheme.colorScheme.error
                        },
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    disabledContainerColor = MaterialTheme.colorScheme.outline,
                    disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                )
            },
        content = {
            if (isNotAnswered) {
                Icon(
                    painterResource(Res.drawable.ic_check),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp),
                )
            }
            Text(
                modifier = Modifier.padding(8.dp),
                text = if (isNotAnswered) "Comprobar" else "Continuar",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
            )
        },
    )
}

@Composable
fun AbsoluteQuestionHeader(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    answerState: AnswerState,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (answerState) {
            AnswerState.NotAnsweredYet, AnswerState.NotSubmittedYet -> {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
            }
            AnswerState.Correct -> {
                Text(
                    text = "¡Correcto!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = "Bien hecho",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            AnswerState.Incorrect -> {
                Text(
                    text = "Incorrecto",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = "Puedes hacerlo mejor",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun AbsoluteNoteOptionCard(
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
                        vertical = 8.dp,
                        horizontal = 16.dp,
                    ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Icon(
                painter =
                    painterResource(noteOption.absoluteNote.drawableResource),
                modifier =
                    Modifier
                        .width(80.dp),
                contentDescription = "",
                tint = contentColor,
            )
            Text(
                text = noteOption.absoluteNote.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = contentColor,
            )
        }
    }
}
