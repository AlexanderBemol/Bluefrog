package com.amontdevs.bluefrog.ui.screens.login.login

import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.common.TextFieldState

data class LoginMailViewState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isLoginButtonEnabled: Boolean = false,
    val showPassword: Boolean = false,
)

enum class LoginMailTextFields {
    Email,
    Password,
}

sealed class LoginMailViewEvent {
    data class ShowToast(
        val toast: CustomToast,
    ) : LoginMailViewEvent()

    data class Navigate(
        val destination: LoginNavigation,
    ) : LoginMailViewEvent()
}
