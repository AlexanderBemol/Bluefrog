package com.amontdevs.bluefrog.ui.screens.login.status

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.amontdevs.bluefrog.ui.screens.common.BackButtonRow
import com.amontdevs.bluefrog.ui.theme.P3
import com.amontdevs.bluefrog.ui.theme.PrimaryButton
import com.amontdevs.bluefrog.ui.theme.PrimaryOutlinedButton
import com.amontdevs.bluefrog.ui.utils.FullScreenPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginStatusScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val viewModel = koinViewModel<LoginStatusViewModel>()
    LoginStatusScreen(
        modifier = modifier,
        stateFlow = viewModel.state,
        getCurrentUser = { viewModel.getCurrentUser() },
        retrieveSession = { viewModel.retrieveUserForCurrentSession() },
        onSignOutClicked = { viewModel.signOut() },
        onBackClicked = { navController.popBackStack() },
    )
}

@Composable
fun LoginStatusScreen(
    modifier: Modifier = Modifier,
    stateFlow: StateFlow<LoginStatusStateView>,
    getCurrentUser: () -> Unit = {},
    retrieveSession: () -> Unit = {},
    onSignOutClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        BackButtonRow { onBackClicked() }

        Column(Modifier.fillMaxWidth()) {
            TextRow(title = "aud:", content = stateFlow.value.aud)
            Spacer(modifier = Modifier.height(P3))
            TextRow(title = "confirmation_sent_at", content = stateFlow.value.confirmationSentAt)
            Spacer(modifier = Modifier.height(P3))
            TextRow(title = "confirmed_at", content = stateFlow.value.confirmedAt)
            Spacer(modifier = Modifier.height(P3))
            TextRow(title = "created_at", content = stateFlow.value.createdAt)
            Spacer(modifier = Modifier.height(P3))
            TextRow(title = "email", content = stateFlow.value.email)
            Spacer(modifier = Modifier.height(P3))
            TextRow(title = "email_confirmed_at", content = stateFlow.value.emailConfirmedAt)
        }
        Column(Modifier.fillMaxWidth()) {
            PrimaryOutlinedButton(
                text = "Get Current User",
                modifier = Modifier.fillMaxWidth(),
                onClick = { getCurrentUser() },
            )
            Spacer(modifier = Modifier.height(P3))
            PrimaryOutlinedButton(
                text = "Retrieve Session",
                modifier = Modifier.fillMaxWidth(),
                onClick = { retrieveSession() },
            )
            Spacer(modifier = Modifier.height(P3))
            PrimaryButton(
                text = "Sign Out",
                onClick = { onSignOutClicked() },
            )
        }
    }
}

@Composable
private fun TextRow(
    title: String,
    content: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            style =
                TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 24.sp,
                ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = content,
            style =
                TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                ),
        )
    }
}

@Composable
@Preview
fun LoginStatusScreenPreview() {
    FullScreenPreview {
        LoginStatusScreen(
            stateFlow = MutableStateFlow(LoginStatusStateView()),
        )
    }
}
