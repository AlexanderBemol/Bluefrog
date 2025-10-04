package com.amontdevs.bluefrog.ui.screens.login.login

import com.amontdevs.bluefrog.ui.screens.common.TextFieldState

data class LoginViewState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val userLogged: Boolean = false,
)

enum class LoginTextFields {
    Email,
    Password,
}
