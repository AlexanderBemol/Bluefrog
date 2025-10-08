package com.amontdevs.bluefrog.ui.screens.login.login

import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation

sealed class LoginViewEvent {
    data class ShowToast(
        val toast: CustomToast,
    ) : LoginViewEvent()

    data class Navigate(
        val destination: LoginNavigation,
    ) : LoginViewEvent()
}