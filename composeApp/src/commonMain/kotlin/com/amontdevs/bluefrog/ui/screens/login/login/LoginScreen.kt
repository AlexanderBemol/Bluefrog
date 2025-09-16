package com.amontdevs.bluefrog.ui.screens.login.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.login.common.StartSocialAccessFooter
import com.amontdevs.bluefrog.ui.theme.P0
import com.amontdevs.bluefrog.ui.theme.P3
import com.amontdevs.bluefrog.ui.theme.P5
import com.amontdevs.bluefrog.ui.theme.PrimaryButton
import com.amontdevs.bluefrog.ui.theme.PrimaryTextButton
import com.amontdevs.bluefrog.ui.utils.FullScreenPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginNavController: NavController,
    loginViewModel: LoginViewModel = koinViewModel()
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
            modifier = modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = "Welcome Back",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                )
                Spacer(Modifier.height(P0))
                Text(
                    text = "Log In to continue",
                    fontSize = 18.sp,
                )
            }
            Column {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    value = state.value.email.value,
                    isError = state.value.email.error != null,
                    supportingText = {
                        if (state.value.email.error != null) {
                            Text(state.value.email.error.toString())
                        }
                    },
                    singleLine = true,
                    onValueChange = {
                        updateTextField(it, LoginTextFields.Email)
                    },
                )
                Spacer(Modifier.height(P3))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    value = state.value.password.value,
                    isError = state.value.password.error != null,
                    supportingText = {
                        if (state.value.password.error != null) {
                            Text(state.value.password.error.toString())
                        }
                    },
                    singleLine = true,
                    onValueChange = {
                        updateTextField(it, LoginTextFields.Password)
                    },
                )
                Spacer(Modifier.height(P5))
                PrimaryButton(
                    text = "Login",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onLoginClick,
                )
                Spacer(Modifier.height(P3))
                PrimaryTextButton(
                    text = "Forgot Password?",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = navigateToRestorePassword,
                )
            }
        }
        StartSocialAccessFooter(Modifier)
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
