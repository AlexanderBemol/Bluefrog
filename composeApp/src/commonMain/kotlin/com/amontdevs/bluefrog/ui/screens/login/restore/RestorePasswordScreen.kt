package com.amontdevs.bluefrog.ui.screens.login.restore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.amontdevs.bluefrog.ui.screens.common.BackButtonRow
import com.amontdevs.bluefrog.ui.theme.P0
import com.amontdevs.bluefrog.ui.theme.P2
import com.amontdevs.bluefrog.ui.theme.PrimaryButton
import com.amontdevs.bluefrog.ui.utils.FullScreenPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RestorePasswordScreen(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            BackButtonRow { }
            Text(
                text = "Restore your password",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 26.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(P2))
            Text(
                text = "Write your email and we’ll send you a mail with the instructions to restore your password.",
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Email") },
            value = "mail",
            onValueChange = {},
        )
        PrimaryButton(
            text = "Restore",
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
        )
    }
}

@Composable
@Preview
private fun RestorePasswordScreenPreview() {
    FullScreenPreview {
        RestorePasswordScreen(Modifier)
    }
}
