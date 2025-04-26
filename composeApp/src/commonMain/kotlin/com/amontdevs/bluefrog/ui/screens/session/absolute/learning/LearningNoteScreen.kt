package com.amontdevs.bluefrog.ui.screens.session.absolute.learning

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.amontdevs.bluefrog.ui.screens.session.AbsoluteQuestionHeader
import com.amontdevs.bluefrog.ui.screens.session.CheckButton
import com.amontdevs.bluefrog.ui.screens.session.absolute.AbsoluteQuestion
import com.amontdevs.bluefrog.ui.screens.session.absolute.AnswerState
import com.amontdevs.bluefrog.ui.screens.session.absolute.NoteOption
import com.amontdevs.bluefrog.ui.screens.session.absolute.note.OptionCard
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LearningNotesScreen(
    modifier: Modifier = Modifier,
    absoluteNotesLearningState: AbsoluteQuestion.AbsoluteNotesLearningState,
    onOptionSelected: (selectedNote: NoteOption) -> Unit,
    onContinueClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AbsoluteQuestionHeader(
                modifier = Modifier.fillMaxWidth(),
                title = "Notas Absolutas",
                subtitle = "Presiona en cada nota para escuchar su sonido",
                answerState = AnswerState.NotAnsweredYet
            )
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            absoluteNotesLearningState.noteOptions.forEach { option ->
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
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onContinueClick,
                content = {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Continuar",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
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
    val borderColor = MaterialTheme.colorScheme.outline
    val backgroundColor = MaterialTheme.colorScheme.background
    val contentColor = MaterialTheme.colorScheme.onBackground

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
fun LearningNoteScreenPreview() {
    BlueFrogTheme {
        Scaffold { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .padding(16.dp)
            ) {
                LearningNotesScreen(
                    absoluteNotesLearningState = AbsoluteQuestion.AbsoluteNotesLearningState(
                        noteOptions = listOf(
                            NoteOption(AbsoluteNote.C3),
                            NoteOption(AbsoluteNote.D3),
                            NoteOption(AbsoluteNote.E3)
                        )
                    ),
                    onOptionSelected = {},
                    onContinueClick = {}
                )
            }
        }
    }
}