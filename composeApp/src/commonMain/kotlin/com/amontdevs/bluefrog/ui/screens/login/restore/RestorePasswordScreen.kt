package com.amontdevs.bluefrog.ui.screens.login.restore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigator
import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.ic_mail
import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.screens.common.BackButtonRow
import com.amontdevs.bluefrog.ui.screens.login.signin.SignInTextFields
import com.amontdevs.bluefrog.ui.theme.P2
import com.amontdevs.bluefrog.ui.theme.PrimaryButton
import com.amontdevs.bluefrog.ui.utils.FullScreenPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun RestorePasswordScreen(
    modifier: Modifier = Modifier,
    loginNavController: NavController,
    showToast: (CustomToast) -> Unit = {},
) {
    val viewModel = koinInject<RestorePasswordViewModel>()
    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect {
            when (it) {
                is RestorePasswordViewEvent.Navigate -> loginNavController.navigate(it)
                is RestorePasswordViewEvent.ShowToast -> showToast(it.toast)
            }
        }
    }
    RestorePasswordScreen(
        modifier = modifier,
        stateFlow = viewModel.state,
        navigateBack = { loginNavController.popBackStack() },
        updateTextField = viewModel::updateEmail,
        restorePassword = viewModel::resetPassword,
    )
}

@Composable
fun RestorePasswordScreen(
    modifier: Modifier,
    stateFlow: StateFlow<RestorePasswordViewState>,
    navigateBack: () -> Unit = {},
    updateTextField: (String) -> Unit = {},
    restorePassword: () -> Unit = {},
) {
    val state = stateFlow.collectAsStateWithLifecycle()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            BackButtonRow { navigateBack() }
            Text(
                text = "Restore your password",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 26.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(P2))
            Text(
                text = "Write your email and we’ll send you a mail with the instructions to restore your password.",
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Email") },
            placeholder = { Text("user@example.com") },
            value = state.value.email.value,
            isError = state.value.email.error != null,
            singleLine = true,
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
            supportingText = {
                if (state.value.email.error != null) {
                    Text(state.value.email.error!!)
                }
            },
            onValueChange = {
                updateTextField(it)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_mail),
                    contentDescription = "Mail Icon",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
        )
        PrimaryButton(
            text = "Restore",
            enabled = state.value.isRestoreButtonEnabled,
            modifier = Modifier.fillMaxWidth(),
            onClick = restorePassword,
        )
    }
}

@Composable
@Preview
private fun RestorePasswordScreenPreview() {
    FullScreenPreview {
        RestorePasswordScreen(
            modifier = Modifier,
            stateFlow = MutableStateFlow(RestorePasswordViewState()),
        )
    }
}
