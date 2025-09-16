package com.amontdevs.bluefrog.ui.utils

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amontdevs.bluefrog.ui.navigation.BottomNavigationItem
import com.amontdevs.bluefrog.ui.navigation.CustomBottomNavigationBar
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme

@Composable
fun FullScreenPreview(
    darkTheme: Boolean = false,
    showBottomBar: Boolean = false,
    content: @Composable () -> Unit,
) {
    BlueFrogTheme(
        darkTheme = darkTheme,
    ) {
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    CustomBottomNavigationBar(
                        selectedItem = BottomNavigationItem.HOME,
                        onItemSelected = {},
                    )
                }
            },
        ) { paddingValues ->
            Surface(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
            ) {
                content()
            }
        }
    }
}

@Composable
fun ContentPreview(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    BlueFrogTheme(
        darkTheme = darkTheme,
    ) {
        Surface {
            content()
        }
    }
}
