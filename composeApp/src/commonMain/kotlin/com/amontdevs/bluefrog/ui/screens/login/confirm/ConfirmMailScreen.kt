package com.amontdevs.bluefrog.ui.screens.login.confirm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.theme.P2
import com.amontdevs.bluefrog.ui.theme.P3
import com.amontdevs.bluefrog.ui.theme.PrimaryButton
import com.amontdevs.bluefrog.ui.theme.PrimaryOutlinedButton
import com.amontdevs.bluefrog.ui.utils.FullScreenPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ConfirmMailScreen(
    modifier: Modifier = Modifier,
    loginNavigation: LoginNavigation
) {

}

@Composable
fun ConfirmMailScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                text = "Confirm your email",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 26.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(P2))
            Text(
                text =
                    "We sent you and email to: user@mail.com to confirm your account, " +
                        "please look for it and follow the instructions.",
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            PrimaryButton(
                text = "Check again",
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
            )
            Spacer(Modifier.height(P3))
            PrimaryOutlinedButton(
                text = "Send again",
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
            )
            Spacer(Modifier.height(P3))
            Text(
                text = "Didn’t receive the mail? Check your spam folder.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
@Preview
private fun ConfirmMailScreenPreview() {
    FullScreenPreview {
        ConfirmMailScreen()
    }
}
