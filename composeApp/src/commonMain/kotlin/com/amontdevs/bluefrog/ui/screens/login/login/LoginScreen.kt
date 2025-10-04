package com.amontdevs.bluefrog.ui.screens.login.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ic_mail
import bluefrog.composeapp.generated.resources.ill_frog_face
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.common.BackButtonRow
import com.amontdevs.bluefrog.ui.theme.AppleButton
import com.amontdevs.bluefrog.ui.theme.GoogleButton
import com.amontdevs.bluefrog.ui.theme.P3
import com.amontdevs.bluefrog.ui.theme.P5
import com.amontdevs.bluefrog.ui.theme.PrimaryButton
import com.amontdevs.bluefrog.ui.utils.FullScreenPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginNavController: NavController,
    loginViewModel: LoginViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        loginViewModel.navigationEvent.collect {
            loginNavController.navigate(it)
        }
    }

    LoginScreen(
        modifier = modifier,
        stateFlow = loginViewModel.state,
        navigateToRestorePassword = {
            loginNavController.navigate(LoginNavigation.RestorePassword)
        },
        updateTextField = { value, field ->
            loginViewModel.updateTexField(value, field)
        },
        onLoginClick = {
            loginViewModel.login()
        },
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    stateFlow: StateFlow<LoginViewState>,
    navigateToRestorePassword: () -> Unit = {},
    updateTextField: (String, LoginTextFields) -> Unit = { _, _ -> },
    onLoginClick: () -> Unit = {},
) {
    val state = stateFlow.collectAsStateWithLifecycle()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            BackButtonRow { }
            Text(
                text = "Welcome Back",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 26.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(P3))
            Text(
                text = "Log In to continue",
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(Res.drawable.ill_frog_face),
                contentDescription = "Bluefrog Logo",
                modifier = Modifier.width(200.dp),
                contentScale = ContentScale.FillWidth,
            )
        }
        Column {
            PrimaryButton(
                text = "Login with mail",
                modifier = Modifier.fillMaxWidth(),
                onClick = onLoginClick,
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
                onClick = {},
            )
            Spacer(Modifier.height(P3))
            AppleButton(
                text = "Continue with Apple",
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
            )
            Spacer(Modifier.height(P5))
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
private fun LoginScreenPreview() {
    FullScreenPreview {
        LoginScreen(
            modifier = Modifier,
            stateFlow = MutableStateFlow(LoginViewState()),
        )
    }
}
