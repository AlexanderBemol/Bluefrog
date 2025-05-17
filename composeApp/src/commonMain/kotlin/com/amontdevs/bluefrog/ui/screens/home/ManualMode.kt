package com.amontdevs.bluefrog.ui.screens.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ill_custom_session
import com.amontdevs.bluefrog.domain.absolute.CustomSession
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun ManualModeScreen(
    modifier: Modifier = Modifier,
    manualModeViewModel: ManualModeViewModel = koinInject(),
) {
    val manualModeStateFlow = manualModeViewModel.manualModeState
    ManualModeScreen(
        modifier = modifier,
        manualModeState = manualModeStateFlow,
        onCustomSessionClick = {},
    )
}

@Composable
fun ManualModeScreen(
    modifier: Modifier = Modifier,
    manualModeState: StateFlow<ManualModeState>,
    onCustomSessionClick: () -> Unit,
) {
    val manualModeState = manualModeState.collectAsState()
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = "Manual Mode",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = "Choose a predefined session or create your own custom session.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
        )
        Spacer(Modifier.size(32.dp))
        CreateCustomSessionCard(
            onCustomSessionClick = onCustomSessionClick,
        )
        Spacer(Modifier.size(32.dp))
        Text(
            text = "Sessions",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.size(8.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = manualModeState.value.sessions,
            ) {
                CustomSessionCard(
                    customSession = it,
                    onStartSessionClick = {},
                )
            }
        }
    }
}

@Composable
fun CreateCustomSessionCard(onCustomSessionClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp).height(IntrinsicSize.Min),
        ) {
            Column(
                modifier = Modifier.weight(4f).fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = "Custom Session",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "Choose the notes you want to practice.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onCustomSessionClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors =
                        ButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                            disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.outline,
                        ),
                ) {
                    Text(
                        text = "Create",
                    )
                }
            }
            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(Res.drawable.ill_custom_session),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
fun CustomSessionCard(
    customSession: CustomSession,
    onStartSessionClick: () -> Unit,
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp).height(IntrinsicSize.Min),
        ) {
            Column(
                modifier = Modifier.weight(3f).padding(end = 16.dp),
            ) {
                Text(
                    text = customSession.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = customSession.description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                Button(
                    onClick = onStartSessionClick,
                    colors =
                        ButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary,
                            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.outline,
                        ),
                ) {
                    Text(
                        text = "Start",
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ManualModeScreenPreview() {
    BlueFrogTheme(
        darkTheme = false,
    ) {
        Scaffold { paddingValues ->
            Surface(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues)
                        .padding(16.dp),
            ) {
                ManualModeScreen(
                    onCustomSessionClick = {},
                    manualModeState =
                        MutableStateFlow(
                            ManualModeState(
                                sessions =
                                    listOf(
                                        CustomSession(
                                            isPredefined = true,
                                            title = "Level 1",
                                            description = "Starts with C3, D3 and E3",
                                            notes = listOf(),
                                        ),
                                        CustomSession(
                                            isPredefined = true,
                                            title = "Level 2",
                                            description = "Adds F3, G3 and A3 to the previous session",
                                            notes = listOf(),
                                        ),
                                        CustomSession(
                                            isPredefined = true,
                                            title = "Level 3",
                                            description = "Adds B3, C#3 and D#3 to the previous session",
                                            notes = listOf(),
                                        ),
                                        CustomSession(
                                            isPredefined = true,
                                            title = "Level 4",
                                            description = "Adds F#3, G#3 and A#3 to the previous session",
                                            notes = listOf(),
                                        ),
                                        CustomSession(
                                            isPredefined = true,
                                            title = "Level 5",
                                            description = "Starts with C3, D3 and E3",
                                            notes = listOf(),
                                        ),
                                    ),
                            ),
                        ),
                )
            }
        }
    }
}
