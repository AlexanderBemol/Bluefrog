package com.amontdevs.bluefrog.ui.utils

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme

@Composable
fun ScreenPreview(content: @Composable (RowScope.() -> Unit)) {
    BlueFrogTheme {
        Scaffold { paddingValues ->
            Surface(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues)
                        .padding(16.dp),
            ) {
                content
            }
        }
    }
}
