package com.amontdevs.bluefrog.ui.screens.login.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.login.common.StartSocialAccessFooter
import com.amontdevs.bluefrog.ui.theme.P0
import com.amontdevs.bluefrog.ui.theme.P1
import com.amontdevs.bluefrog.ui.theme.P3
import com.amontdevs.bluefrog.ui.theme.P5
import com.amontdevs.bluefrog.ui.theme.PrimaryButton
import com.amontdevs.bluefrog.ui.utils.FullScreenPreview
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composable.GoogleDialogType
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = koinViewModel(),
    loginNavController: NavController,
    showToast: (CustomToast) -> Unit = {},
) {
    val composeAuth = koinInject<ComposeAuth>()
    composeAuth.rememberSignInWithGoogle(
        onResult = { viewModel.handleGoogleSignInResult(it) },
        type = GoogleDialogType.DIALOG,
    )

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect {
            when (it) {
                is SignInViewEvent.Navigate -> loginNavController.navigate(it.destination)
                is SignInViewEvent.ShowToast -> showToast(it.toast)
            }
        }
    }

    SignInScreen(
        modifier = modifier,
        stateFlow = viewModel.state,
        onSignInClicked = {
            viewModel.signIn()
            loginNavController.navigate(LoginNavigation.ConfirmMail)
        },
        updateTextField = { field, value ->
            viewModel.updateTexField(value, field)
        },
    )
}

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    stateFlow: StateFlow<SignInViewState>,
    onSignInClicked: () -> Unit = {},
    updateTextField: (SignInTextFields, String) -> Unit = { _, _ ->
    },
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
                    text = "Welcome to Bluefrog",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                )
                Spacer(Modifier.height(P0))
                Text(
                    text = "Create an account to continue",
                    fontSize = 18.sp,
                )
            }
            Column {
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
                        updateTextField(SignInTextFields.Email, it)
                    },
                )
                Spacer(Modifier.height(P1))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    placeholder = { Text("********") },
                    singleLine = true,
                    value = state.value.password.value,
                    isError = state.value.password.error != null,
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next,
                        ),
                    supportingText = {
                        if (state.value.password.error != null) {
                            Text(state.value.password.error!!)
                        }
                    },
                    onValueChange = {
                        updateTextField(SignInTextFields.Password, it)
                    },
                )
                Spacer(Modifier.height(P1))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Confirm your password") },
                    placeholder = { Text("********") },
                    singleLine = true,
                    value = state.value.confirmPassword.value,
                    isError = state.value.confirmPassword.error != null,
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                        ),
                    supportingText = {
                        if (state.value.confirmPassword.error != null) {
                            Text(state.value.confirmPassword.error!!)
                        }
                    },
                    onValueChange = {
                        updateTextField(SignInTextFields.ConfirmPassword, it)
                    },
                )
                Spacer(Modifier.height(P5))
                PrimaryButton(
                    text = "Sign In",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.value.signInButtonEnabled,
                    onClick = onSignInClicked,
                )
                Spacer(Modifier.height(P3))
            }
        }
        StartSocialAccessFooter(Modifier)
    }
}

@Composable
@Preview()
private fun SignInScreenPreview() {
    FullScreenPreview {
        SignInScreen(
            stateFlow = MutableStateFlow(SignInViewState()),
        )
    }
}
