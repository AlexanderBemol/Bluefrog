package com.amontdevs.bluefrog.ui.screens.login.signin

import com.amontdevs.bluefrog.ui.screens.login.common.TextFieldState

data class SignInViewState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val confirmPassword: TextFieldState = TextFieldState(),
    val signInButtonEnabled: Boolean = false,
)

enum class SignInTextFields {
    Email,
    Password,
    ConfirmPassword,
}
