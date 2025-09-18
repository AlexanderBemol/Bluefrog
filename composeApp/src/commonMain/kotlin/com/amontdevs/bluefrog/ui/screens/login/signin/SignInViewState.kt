package com.amontdevs.bluefrog.ui.screens.login.signin

import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.login.common.TextFieldState

data class SignInViewState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val confirmPassword: TextFieldState = TextFieldState(),
    val signInButtonEnabled: Boolean = false,
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
