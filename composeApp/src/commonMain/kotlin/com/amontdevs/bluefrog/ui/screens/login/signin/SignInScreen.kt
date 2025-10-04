package com.amontdevs.bluefrog.ui.screens.login.signin

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.amontdevs.bluefrog.ui.theme.P2
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
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = koinViewModel(),
    loginNavController: NavController,
    showToast: (CustomToast) -> Unit = {},
) {
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
        onBackClicked = {
            loginNavController.popBackStack()
        },
        changePasswordVisibility = {
            viewModel.changePasswordVisibility()
        },
        changeConfirmPasswordVisibility = {
            viewModel.changeConfirmPasswordVisibility()
        },
    )
}

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    stateFlow: StateFlow<SignInViewState>,
    onSignInClicked: () -> Unit = {},
    updateTextField: (SignInTextFields, String) -> Unit = { _, _ -> },
    onBackClicked: () -> Unit = {},
    changePasswordVisibility: () -> Unit = {},
    changeConfirmPasswordVisibility: () -> Unit = {},
) {
    val state = stateFlow.collectAsStateWithLifecycle()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        BackButtonRow { onBackClicked() }
        Column(
            modifier = modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Spacer(Modifier.height(P3))
                Text(
                    text = "Welcome to Bluefrog",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    lineHeight = 26.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(P2))
                Text(
                    text = "Create an account to continue",
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
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
                    label = { Text("Password") },
                    placeholder = { Text("********") },
                    singleLine = true,
                    value = state.value.password.value,
                    isError = state.value.password.error != null,
                    visualTransformation =
                        if (state.value.showPassword.not()) {
                            PasswordVisualTransformation()
                        } else {
                            VisualTransformation.None
                        },
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next,
                        ),
                    onValueChange = {
                        updateTextField(SignInTextFields.Password, it)
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
                        IconButton(changePasswordVisibility) {
                            Icon(
                                painter =
                                    if (state.value.showPassword.not()) {
                                        painterResource(Res.drawable.ic_visibilty)
                                    } else {
                                        painterResource(Res.drawable.ic_visibilty_off)
                                    },
                                contentDescription = "Mail Icon",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    },
                )
                Spacer(Modifier.height(P3))
                StrengthIndicatorRow(
                    supportingText = state.value.passwordStrengthText,
                    strengthLevel = state.value.passwordStrength,
                )
                Spacer(Modifier.height(P1))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Confirm your password") },
                    placeholder = { Text("********") },
                    singleLine = true,
                    value = state.value.confirmPassword.value,
                    isError = state.value.confirmPassword.error != null,
                    visualTransformation =
                        if (state.value.showConfirmPassword.not()) {
                            PasswordVisualTransformation()
                        } else {
                            VisualTransformation.None
                        },
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                        ),
                    onValueChange = {
                        updateTextField(SignInTextFields.ConfirmPassword, it)
                    },
                    supportingText = {
                        if (state.value.confirmPassword.error != null) {
                            Text(state.value.confirmPassword.error!!)
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
                        IconButton(changeConfirmPasswordVisibility) {
                            Icon(
                                painter =
                                    if (state.value.showConfirmPassword.not()) {
                                        painterResource(Res.drawable.ic_visibilty)
                                    } else {
                                        painterResource(Res.drawable.ic_visibilty_off)
                                    },
                                contentDescription = "Mail Icon",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    },
                )
                Spacer(Modifier.height(P5))
                PrimaryButton(
                    text = "Sign In",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.value.signInButtonEnabled,
                    onClick = onSignInClicked,
                )
            }
        }
    }
}

@Composable
private fun StrengthIndicatorRow(
    supportingText: String = "",
    strengthLevel: Int = 0,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        for (i in 0 until 4) {
            val animatedColor by animateColorAsState(
                targetValue = getBarColor(i, strengthLevel),
                animationSpec =
                    tween(
                        durationMillis = 200,
                        delayMillis = i,
                    ),
                label = "colorAnimation",
            )
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            color = animatedColor,
                        ),
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = supportingText,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
fun getBarColor(
    barIndex: Int,
    strengthLevel: Int,
): Color =
    if (barIndex < strengthLevel) {
        when (strengthLevel) {
            1, 2 -> {
                MaterialTheme.colorScheme.error
            }
            3 -> {
                MaterialTheme.colorScheme.tertiary
            }
            else -> {
                MaterialTheme.colorScheme.secondary
            }
        }
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
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

@Composable
@Preview()
private fun SignInScreenDarkPreview() {
    FullScreenPreview(darkTheme = true) {
        SignInScreen(
            stateFlow = MutableStateFlow(SignInViewState()),
        )
    }
}
