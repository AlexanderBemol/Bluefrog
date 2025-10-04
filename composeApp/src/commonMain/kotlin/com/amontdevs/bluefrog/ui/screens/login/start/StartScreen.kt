package com.amontdevs.bluefrog.ui.screens.login.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ill_frog_face
import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.dialog.KindOfToast
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.theme.P3
import com.amontdevs.bluefrog.ui.theme.PrimaryButton
import com.amontdevs.bluefrog.ui.theme.PrimaryOutlinedButton
import com.amontdevs.bluefrog.ui.utils.FullScreenPreview
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composable.GoogleDialogType
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    startViewModel: StartViewModel = koinViewModel(),
    loginNavController: NavController,
    showToast: (CustomToast) -> Unit = {},
) {
    val composeAuth = koinInject<ComposeAuth>()
    val googleAuthState =
        composeAuth.rememberSignInWithGoogle(
            onResult = { startViewModel.handleGoogleSignInResult(it) },
            type = GoogleDialogType.DIALOG,
        )

    LaunchedEffect(Unit) {
        startViewModel.navigationEvent.collect {
            loginNavController.navigate(it)
        }
    }

    StartScreen(
        modifier = modifier,
        startGoogleAuthFlow = { googleAuthState.startFlow() },
        startFacebookAuthFlow = {
            showToast(
                CustomToast(
                    title = "Something went wrong",
                    message = "Please try again later",
                    kindOfToast = KindOfToast.Error,
                ),
            )
        },
        navigateToSetup = { loginNavController.navigate(LoginNavigation.Setup) },
        navigateToLogin = { loginNavController.navigate(LoginNavigation.Login) },
    )
}

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    startGoogleAuthFlow: () -> Unit = {},
    startFacebookAuthFlow: () -> Unit = {},
    navigateToSetup: () -> Unit = {},
    navigateToLogin: () -> Unit = {},
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
                Spacer(Modifier.height(P3))
                Text(
                    text = "Your personal ear training coach",
                    fontSize = 22.sp,
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                PrimaryButton(
                    text = "Get Started",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = navigateToSetup,
                )
                Spacer(Modifier.height(P3))
                PrimaryOutlinedButton(
                    text = "I already have an account",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = navigateToLogin,
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

@Composable
@Preview()
private fun StartScreenPreview() {
    FullScreenPreview(
        darkTheme = false,
        showBottomBar = false,
    ) {
        StartScreen()
    }
}
