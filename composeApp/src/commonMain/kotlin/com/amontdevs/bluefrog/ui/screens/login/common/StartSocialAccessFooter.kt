package com.amontdevs.bluefrog.ui.screens.login.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ic_facebook
import bluefrog.composeapp.generated.resources.ic_google
import com.amontdevs.bluefrog.ui.theme.P3
import com.amontdevs.bluefrog.ui.theme.P6
import com.amontdevs.bluefrog.ui.utils.ContentPreview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun StartSocialAccessFooter(
    modifier: Modifier,
    onGoogleClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(P3))
        Text(
            text = "Or Access With:",
            fontSize = 20.sp,
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            IconButton(
                modifier = Modifier.size(P6),
                onClick = onGoogleClick,
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_google),
                    contentDescription = "Google Icon",
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Spacer(Modifier.width(32.dp))

            IconButton(
                modifier = Modifier.size(P6),
                onClick = onFacebookClick,
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_facebook),
                    contentDescription = "Facebook Icon",
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
@Preview()
private fun StartSocialAccessFooterPreview() {
    ContentPreview {
        StartSocialAccessFooter(Modifier)
    }
}
