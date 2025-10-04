package com.amontdevs.bluefrog.ui.screens.debug

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.theme.P1
import com.amontdevs.bluefrog.ui.theme.P2
import com.amontdevs.bluefrog.ui.theme.P3
import com.amontdevs.bluefrog.ui.theme.PrimaryButton
import com.amontdevs.bluefrog.ui.theme.SecondaryButton
import com.amontdevs.bluefrog.ui.utils.BlueFrogAppBar

@Composable
fun DebugMenuScreen(
    navController: NavController
) {
    val loginScreens = LoginNavigation::class.sealedSubclasses
        .mapNotNull { it.objectInstance }
        .map { it to it::class.simpleName.toString() }

    Scaffold(
        topBar = {
            BlueFrogAppBar(
                title = "Debug Menu",
                showBackButton = true,
                onBackPressed = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(P3),
            verticalArrangement = Arrangement.spacedBy(P2)
        ) {
            item {
                Text(
                    text = "This screen is for testing purposes only. " +
                        "Please do not use it in production.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(P3))
            }

            item {
                LoginScreensCard(
                    loginScreens = loginScreens,
                    onScreenSelected = { screen ->
                        navController.navigate(screen)
                    }
                )
            }

            item {
                MockLoginStateCard()
            }
        }
    }
}

@Composable
private fun LoginScreensCard(
    loginScreens: List<Pair<LoginNavigation, String>>,
    onScreenSelected: (LoginNavigation) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(P3)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Login Screens",
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(P2))
                loginScreens.forEach { (screen, name) ->
                    PrimaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = name,
                        onClick = { onScreenSelected(screen) }
                    )
                    Spacer(modifier = Modifier.height(P1))
                }
            }
        }
    }
}

@Composable
private fun MockLoginStateCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(P3)
        ) {
            Text(
                text = "Mock Login State",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(P2))
            SecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Mock Login State",
                onClick = { /* TODO */ }
            )
        }
    }
}