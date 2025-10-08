package com.amontdevs.bluefrog.ui.screens.login.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ic_mail
import bluefrog.composeapp.generated.resources.ic_password
import bluefrog.composeapp.generated.resources.ic_visibilty
import bluefrog.composeapp.generated.resources.ic_visibilty_off
import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.common.BackButtonRow
import com.amontdevs.bluefrog.ui.theme.P1
import com.amontdevs.bluefrog.ui.theme.P3
import com.amontdevs.bluefrog.ui.theme.PrimaryButton
import com.amontdevs.bluefrog.ui.theme.PrimaryTextButton
import com.amontdevs.bluefrog.ui.utils.FullScreenPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginMailScreen(
    modifier: Modifier = Modifier,
    loginNavController: NavController,
    loginViewModel: LoginMailViewModel = koinViewModel(),
    showToast: (CustomToast) -> Unit = {},
) {
    LaunchedEffect(Unit) {
        loginViewModel.viewEvent.collect {
            when (it) {
                is LoginMailViewEvent.Navigate -> {
                    loginNavController.navigate(it.destination)
                }

                is LoginMailViewEvent.ShowToast -> {
                    showToast(it.toast)
                }
            }
        }
    }

    LoginMailScreen(
        modifier = modifier,
        stateFlow = loginViewModel.state,
        onBackClicked = { loginNavController.popBackStack() },
        onLogin = { loginViewModel.login() },
        updateTextField = { field: LoginMailTextFields, value: String ->
            loginViewModel.updateTexField(value, field)
        },
        updateShowPassword = loginViewModel::toggleShowPassword,
        onRestorePassword = { loginNavController.navigate(LoginNavigation.RestorePassword) },
    )
}

@Composable
fun LoginMailScreen(
    modifier: Modifier = Modifier,
    stateFlow: StateFlow<LoginMailViewState>,
    onBackClicked: () -> Unit = {},
    onLogin: () -> Unit = {},
    onRestorePassword: () -> Unit = {},
    updateTextField: (LoginMailTextFields, String) -> Unit = { _, _ -> },
    updateShowPassword: () -> Unit = {},
) {
    val state = stateFlow.collectAsStateWithLifecycle()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            BackButtonRow { onBackClicked() }
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
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") },
                placeholder = { Text("user@example.com") },
                value = state.value.email.value,
                isError = state.value.email.error != null,
                singleLine = true,
                keyboardOptions =
                    KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                supportingText = {
                    if (state.value.email.error != null) {
                        Text(state.value.email.error!!)
                    }
                },
                onValueChange = {
                    updateTextField(LoginMailTextFields.Email, it)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_mail),
                        contentDescription = "Mail Icon",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
            )
            Spacer(Modifier.height(P1))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Confirm your password") },
                placeholder = { Text("********") },
                singleLine = true,
                value = state.value.password.value,
                isError = false,
                visualTransformation =
                    if (state.value.showPassword.not()) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                keyboardOptions =
                    KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                    ),
                onValueChange = {
                    updateTextField(LoginMailTextFields.Password, it)
                },
                supportingText = {
                    if (state.value.password.error != null) {
                        Text(state.value.password.error!!)
                    }
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_password),
                        contentDescription = "Password Icon",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                trailingIcon = {
                    IconButton(updateShowPassword) {
                        Icon(
                            painter =
                                if (state.value.showPassword.not()) {
                                    painterResource(Res.drawable.ic_visibilty)
                                } else {
                                    painterResource(Res.drawable.ic_visibilty_off)
                                },
                            contentDescription = "Eye Icon",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
            )
        }
        Column {
            PrimaryButton(
                text = "Login",
                enabled = state.value.isLoginButtonEnabled,
                modifier = Modifier.fillMaxWidth(),
                onClick = onLogin,
            )
            Spacer(Modifier.height(P3))
            PrimaryTextButton(
                text = "Restore password",
                modifier = Modifier.fillMaxWidth(),
                onClick = onRestorePassword,
            )
        }
    }
}

@Composable
@Preview()
private fun LoginMailScreenPreview() {
    FullScreenPreview {
        LoginMailScreen(
            modifier = Modifier,
            stateFlow = MutableStateFlow(LoginMailViewState()),
        )
    }
}
