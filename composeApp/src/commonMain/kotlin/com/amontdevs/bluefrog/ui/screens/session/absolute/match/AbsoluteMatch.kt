package com.amontdevs.bluefrog.ui.screens.session.absolute.match

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ic_bars
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun AbsoluteMatch() {
    val notes = listOf("Do","Re","Mi","Fa")
    Column(
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Relaciona",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = "Relaciona cada nota con su respectivo sonido.",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ){
                items(notes.size) {
                    MatchNoteCard(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = notes[it]
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                items(notes.size) {
                    MatchSoundCard(
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                modifier = Modifier
                    .height(48. dp)
                    .fillMaxWidth(),
                onClick = {},
                content = {
                    Text(
                        text = "Continue",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
            )
        }
    }
}

@Composable
fun MatchNoteCard(
    modifier: Modifier = Modifier,
    text: String
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
fun MatchSoundCard(
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter =
                    painterResource(Res.drawable.ic_bars),
                modifier = Modifier
                    .width(60.dp)
                    .padding(vertical = 7.dp),
                contentDescription = ""
            )
        }
    }
}

@Composable
@Preview
fun AbsoluteMatchPreview() {
    BlueFrogTheme {
        Scaffold { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .padding(16.dp)
            ){
                AbsoluteMatch()
            }
        }
    }
}