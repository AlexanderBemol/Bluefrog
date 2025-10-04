package com.amontdevs.bluefrog.ui.screens.login.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ic_mail
import bluefrog.composeapp.generated.resources.ic_no_account
import bluefrog.composeapp.generated.resources.ill_frog_face
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.theme.AppleButton
import com.amontdevs.bluefrog.ui.theme.GoogleButton
import com.amontdevs.bluefrog.ui.theme.OnSurfaceOutlinedButton
import com.amontdevs.bluefrog.ui.theme.P3
import com.amontdevs.bluefrog.ui.theme.P6
import com.amontdevs.bluefrog.ui.theme.PrimaryButton
import com.amontdevs.bluefrog.ui.utils.FullScreenPreview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CreateAccountScreen(
    modifier: Modifier = Modifier,
    loginNavController: NavController,
) {
    /*
    val composeAuth = koinInject<ComposeAuth>()
    composeAuth.rememberSignInWithGoogle(
        onResult = { viewModel.handleGoogleSignInResult(it) },
        type = GoogleDialogType.DIALOG,
    )
     */

    CreateAccountScreen(
        modifier = modifier,
        startGoogleAuthFlow = {},
        startAppleAuthFlow = {},
        navigateToSignIn = { loginNavController.navigate(LoginNavigation.SignIn) },
        continueWithoutAccount = {},
    )
}

@Composable
fun CreateAccountScreen(
    modifier: Modifier = Modifier,
    startGoogleAuthFlow: () -> Unit = {},
    startAppleAuthFlow: () -> Unit = {},
    navigateToSignIn: () -> Unit = {},
    continueWithoutAccount: () -> Unit = {},
) {
    Column(modifier.fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(Res.drawable.ill_frog_face),
                    contentDescription = "Bluefrog Logo",
                    modifier = Modifier.width(200.dp),
                    contentScale = ContentScale.FillWidth,
                )
                Text(
                    text = "Welcome",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "to Bluefrog",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                PrimaryButton(
                    text = "Continue with Mail",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = navigateToSignIn,
                    addIcon = true,
                    icon = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_mail),
                            contentDescription = "Mail Icon",
                            modifier = Modifier.size(24.dp),
                        )
                    },
                )
                Spacer(Modifier.height(P3))
                GoogleButton(
                    text = "Continue with Google",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = startGoogleAuthFlow,
                )
                Spacer(Modifier.height(P3))
                AppleButton(
                    text = "Continue with Apple",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = startAppleAuthFlow,
                )
                Spacer(Modifier.height(P6))
                OnSurfaceOutlinedButton(
                    text = "Continue without account",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = continueWithoutAccount,
                    addIcon = true,
                    icon = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_no_account),
                            contentDescription = "No account icon",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                )
            }
            Text(
                text = "By continuing, you agree to our Terms of Service and Privacy Policy.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun CreateAccountScreenPreview() {
    FullScreenPreview {
        CreateAccountScreen()
    }
}

@Preview()
@Composable
private fun CreateAccountScreenDarkPreview() {
    FullScreenPreview(darkTheme = true) {
        CreateAccountScreen()
    }
}
