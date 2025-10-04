package com.amontdevs.bluefrog.ui.screens.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ic_arrow_back
import org.jetbrains.compose.resources.painterResource

@Composable
fun BackButtonRow(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        IconButton(
            onClick = onClick,
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_arrow_back),
                contentDescription = "Back button",
            )
        }
    }
}
