package com.amontdevs.bluefrog.ui.screens.home.customsession

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ic_close
import com.amontdevs.bluefrog.domain.absolute.AbsoluteNote
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun CustomSessionDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    customSessionViewModel: CustomSessionViewModel = koinInject(),
) {
    CustomSessionDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        customSessionStateFlow = customSessionViewModel.customSessionState,
        onTitleTextChange = {
            customSessionViewModel.onTitleTextFieldValueChanged(it)
        },
        onNoteItemSelect = {
            customSessionViewModel.onCheckAbsoluteNoteItem(it)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSessionDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    customSessionStateFlow: StateFlow<CustomSessionState>,
    onTitleTextChange: (String) -> Unit = {},
    onNoteItemSelect: (AbsoluteNote) -> Unit = {},
) {
    val customSessionState = customSessionStateFlow.collectAsState()
    Dialog(
        onDismissRequest = onDismissRequest,
        properties =
            DialogProperties(
                usePlatformDefaultWidth = false,
            ),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = MaterialTheme.shapes.large, // Optional: for rounded corners
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Custom Session") },
                        navigationIcon = {
                            IconButton(onClick = onDismissRequest) {
                                Icon(
                                    painter = painterResource(resource = Res.drawable.ic_close),
                                    contentDescription = "Close Dialog",
                                )
                            }
                        },
                    )
                },
                bottomBar = {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // Cancel Button
                        OutlinedButton(
                            onClick = onDismissRequest,
                            modifier = Modifier.padding(end = 8.dp),
                        ) {
                            Text("Cancel")
                        }
                        // Save Button
                        Button(
                            onClick = {
                                onDismissRequest() // Dismiss after save
                            },
                        ) {
                            Text("Save")
                        }
                    }
                },
            ) { innerPadding ->
                Column(
                    modifier =
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    Text(
                        text = "Add a title for your session and select your desired notes.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = customSessionState.value.title,
                        onValueChange = onTitleTextChange,
                        label = { Text("Title") },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                        singleLine = true,
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(customSessionState.value.sessionNotes) { item ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onNoteItemSelect(item.note)
                                        }.padding(
                                            vertical = 8.dp,
                                            horizontal = 16.dp,
                                        ),
                            ) {
                                Text(
                                    text = item.note.toString(),
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                                Checkbox(
                                    checked = item.isSelected,
                                    onCheckedChange = { _ ->
                                        onNoteItemSelect(item.note)
                                    },
                                )
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun CustomSessionDialogPreview() {
    BlueFrogTheme {
        Scaffold {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                CustomSessionDialog(
                    onDismissRequest = {},
                    customSessionStateFlow =
                        MutableStateFlow(
                            CustomSessionState(
                                title = "",
                                sessionNotes = AbsoluteNote.entries.map { CustomSessionAbsoluteNoteItem(it) },
                            ),
                        ),
                    onTitleTextChange = {},
                    onNoteItemSelect = {},
                )
            }
        }
    }
}
