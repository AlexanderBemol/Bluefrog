package com.amontdevs.bluefrog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amontdevs.bluefrog.ui.navigation.BottomNavigationItem
import com.amontdevs.bluefrog.ui.navigation.CustomBottomNavigationBar
import com.amontdevs.bluefrog.ui.screens.home.HomeScreen
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val snackBarHostState = remember { SnackbarHostState() }
    BlueFrogTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            bottomBar = {
                CustomBottomNavigationBar(
                    selectedItem = BottomNavigationItem.HOME,
                    onItemSelected = {},
                )
            },
        ) { paddingValues ->
            Surface(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues),
            ) {
                HomeScreen(
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}
