package com.amontdevs.bluefrog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.navigation.AppDestinations
import com.amontdevs.bluefrog.ui.navigation.AppNav
import com.amontdevs.bluefrog.ui.navigation.BottomNavigationItem
import com.amontdevs.bluefrog.ui.navigation.CustomBottomNavigationBar
import com.amontdevs.bluefrog.ui.screens.home.HomeScreen
import com.amontdevs.bluefrog.ui.screens.home.ManualModeScreen
import com.amontdevs.bluefrog.ui.screens.login.LoginContent
import com.amontdevs.bluefrog.ui.screens.session.absolute.StudySession
import com.amontdevs.bluefrog.ui.theme.BlueFrogTheme
import com.amontdevs.bluefrog.ui.theme.P3

@Composable
fun App() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val selectedBottomNavigationItem = remember { mutableStateOf(BottomNavigationItem.HOME) }
    val shouldShowBottomBar = currentDestination?.route in AppDestinations.entries.filter { it.isBottomBarItem }.map { it.route }

    var toastVisible by remember { mutableStateOf(false) }
    var customToast: CustomToast by remember { mutableStateOf(CustomToast()) }

    BlueFrogTheme {
        LoginContent(
            showToast = {
                customToast = it
                toastVisible = true
            },
        )
        CustomToast(
            modifier = Modifier.padding(P3),
            toastVisible = toastVisible,
            customToast = customToast,
            onDismiss = { toastVisible = false },
        )
        /*
        AppContent(
            navController = navController,
            selectedBottomNavigationItem = selectedBottomNavigationItem,
            shouldShowBottomBar = shouldShowBottomBar,
        )
         */
    }
}

@Composable
fun AppContent(
    navController: NavHostController,
    selectedBottomNavigationItem: MutableState<BottomNavigationItem>,
    shouldShowBottomBar: Boolean,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        bottomBar = {
            if (shouldShowBottomBar) {
                CustomBottomNavigationBar(
                    selectedItem = selectedBottomNavigationItem.value,
                    onItemSelected = { item ->
                        selectedBottomNavigationItem.value = item
                        navController.navigate(AppDestinations.geDestinationFromAppBottom(item).route)
                    },
                )
            }
        },
    ) { paddingValues ->
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
        ) {
            NavHost(navController = navController, startDestination = BottomNavigationItem.HOME.name) {
                composable(AppDestinations.HOME_ROUTE.route) {
                    HomeScreen(
                        modifier = Modifier.padding(16.dp),
                        navController = navController,
                    )
                }
                composable(AppDestinations.STATS_ROUTE.route) {
                    Text("Stats")
                }
                composable(AppDestinations.SOCIAL_ROUTE.route) {
                    Text("Social")
                }
                composable(AppDestinations.USER_ROUTE.route) {
                    Text("User")
                }
                composable(AppDestinations.ABSOLUTE_MANUAL_MODE.route) {
                    ManualModeScreen(
                        modifier = Modifier.padding(16.dp),
                        navController = navController,
                    )
                }
                composable<AppNav.AbsoluteSession> { backStackEntry ->
                    val absoluteSession = backStackEntry.toRoute<AppNav.AbsoluteSession>()
                    StudySession(absoluteSession)
                }
            }
        }
    }
}
