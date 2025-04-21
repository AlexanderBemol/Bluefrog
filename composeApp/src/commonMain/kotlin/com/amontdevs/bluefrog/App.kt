package com.amontdevs.bluefrog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.amontdevs.bluefrog.ui.screens.session.absolute.StudySession
import org.jetbrains.compose.ui.tooling.preview.Preview

import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme

@Composable
@Preview
fun App() {
    val snackBarHostState = remember { SnackbarHostState() }
    BlueFrogTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
            ) {
                StudySession()
            }
        }
    }
}