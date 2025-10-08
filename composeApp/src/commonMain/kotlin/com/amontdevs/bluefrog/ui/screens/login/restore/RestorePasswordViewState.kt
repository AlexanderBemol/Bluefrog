package com.amontdevs.bluefrog.ui.screens.login.restore

import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.common.TextFieldState

data class RestorePasswordViewState(
    val email: TextFieldState = TextFieldState(),
    val isRestoreButtonEnabled: Boolean = false,
)

sealed class RestorePasswordViewEvent {
    data class ShowToast(
        val toast: CustomToast,
    ) : RestorePasswordViewEvent()

    data class Navigate(
        val destination: LoginNavigation,
    ) : RestorePasswordViewEvent()
}
