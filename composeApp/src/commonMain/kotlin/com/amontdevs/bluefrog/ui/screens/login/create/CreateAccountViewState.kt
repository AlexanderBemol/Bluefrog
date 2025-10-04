package com.amontdevs.bluefrog.ui.screens.login.create

import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation

sealed class CreateAccountViewEvent {
    data class ShowToast(
        val toast: CustomToast,
    ) : CreateAccountViewEvent()

    data class Navigate(
        val destination: LoginNavigation,
    ) : CreateAccountViewEvent()
}
