package com.amontdevs.bluefrog.ui.screens.login.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.amontdevs.bluefrog.ui.theme.P0
import com.amontdevs.bluefrog.ui.theme.PrimaryButton
import com.amontdevs.bluefrog.ui.utils.FullScreenPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SetupScreen(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = "Setup",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
            )
            Spacer(Modifier.height(P0))
            Text(
                text = "Let´s set up your profile",
                fontSize = 18.sp,
            )
        }
        PrimaryButton(
            text = "Continue",
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
        )
    }
}

@Composable
@Preview
private fun SetupScreenPreview() {
    FullScreenPreview {
        SetupScreen(Modifier)
    }
}
