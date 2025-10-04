package com.amontdevs.bluefrog.ui.navigation

import kotlinx.serialization.Serializable

sealed class LoginNavigation {
    @Serializable
    object Start : LoginNavigation()

    @Serializable
    object CreateAccount : LoginNavigation()

    @Serializable
    object Login : LoginNavigation()

    @Serializable
    object SignIn : LoginNavigation()

    @Serializable
    object RestorePassword : LoginNavigation()

    @Serializable
    object ConfirmMail : LoginNavigation()

    @Serializable
    object Setup : LoginNavigation()

    @Serializable
    object DebugMenu : LoginNavigation()
}
