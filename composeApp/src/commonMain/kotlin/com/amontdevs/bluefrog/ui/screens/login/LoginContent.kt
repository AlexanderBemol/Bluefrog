package com.amontdevs.bluefrog.ui.screens.login

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.login.confirm.ConfirmMailScreen
import com.amontdevs.bluefrog.ui.screens.login.login.LoginScreen
import com.amontdevs.bluefrog.ui.screens.login.restore.RestorePasswordScreen
import com.amontdevs.bluefrog.ui.screens.login.setup.SetupScreen
import com.amontdevs.bluefrog.ui.screens.login.signin.SignInScreen
import com.amontdevs.bluefrog.ui.screens.login.start.StartScreen
import com.amontdevs.bluefrog.ui.theme.P3

@Composable
fun LoginContent() {
    val loginNavController = rememberNavController()
    Scaffold { paddingValues ->
        Surface(Modifier.fillMaxSize().padding(paddingValues)) {
            NavHost(
                navController = loginNavController,
                startDestination = LoginNavigation.Start,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
                popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
            ) {
                composable<LoginNavigation.Start> {
                    StartScreen(
                        modifier = Modifier.padding(P3),
                        loginNavController = loginNavController,
                    )
                }
                composable<LoginNavigation.Login> {
                    LoginScreen(
                        modifier = Modifier.padding(P3),
                        loginNavController = loginNavController,
                    )
                }
                composable<LoginNavigation.SignIn> {
                    SignInScreen(
                        modifier = Modifier.padding(P3),
                        loginNavController = loginNavController,
                    )
                }
                composable<LoginNavigation.RestorePassword> {
                    RestorePasswordScreen(Modifier.padding(P3))
                }
                composable<LoginNavigation.ConfirmMail> {
                    ConfirmMailScreen(Modifier.padding(P3))
                }
                composable<LoginNavigation.Setup> {
                    SetupScreen(Modifier.padding(P3))
                }
            }
        }
    }
}
