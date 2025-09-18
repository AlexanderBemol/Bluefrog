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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ill_frog_face
import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.dialog.KindOfToast
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.login.common.StartSocialAccessFooter
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
        navigateToLogin = { loginNavController.navigate(LoginNavigation.Login) },
        navigateToSignIn = { loginNavController.navigate(LoginNavigation.SignIn) },
    )
}

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    startGoogleAuthFlow: () -> Unit = {},
    startFacebookAuthFlow: () -> Unit = {},
    navigateToLogin: () -> Unit = {},
    navigateToSignIn: () -> Unit = {},
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
                    contentDescription = "",
                    modifier = Modifier.width(200.dp),
                    contentScale = ContentScale.FillWidth,
                )
                Text(
                    text = "BlueFrog",
                    fontSize = 44.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "Ear Training",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                PrimaryButton(
                    text = "Login",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = navigateToLogin,
                )
                Spacer(Modifier.height(P3))
                PrimaryOutlinedButton(
                    text = "Sign In",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = navigateToSignIn,
                )
            }
        }

        StartSocialAccessFooter(
            modifier = Modifier,
            onGoogleClick = startGoogleAuthFlow,
            onFacebookClick = startFacebookAuthFlow,
        )
    }
}

@Composable
@Preview()
private fun StartScreenPreview() {
    FullScreenPreview(
        darkTheme = true,
        showBottomBar = false,
    ) {
        StartScreen()
    }
}
