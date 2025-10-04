package com.amontdevs.bluefrog.ui.screens.login.signin

import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.common.TextFieldState

data class SignInViewState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val showPassword: Boolean = false,
    val confirmPassword: TextFieldState = TextFieldState(),
    val showConfirmPassword: Boolean = false,
    val signInButtonEnabled: Boolean = false,
    val passwordStrength: Int = 0,
    val passwordStrengthText: String = "",
)

sealed class SignInViewEvent {
    data class ShowToast(
        val toast: CustomToast,
    ) : SignInViewEvent()

    data class Navigate(
        val destination: LoginNavigation,
    ) : SignInViewEvent()
}

enum class SignInTextFields {
    Email,
    Password,
    ConfirmPassword,
}
