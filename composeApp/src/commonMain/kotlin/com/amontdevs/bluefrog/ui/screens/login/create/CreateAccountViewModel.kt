package com.amontdevs.bluefrog.ui.screens.login.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amontdevs.bluefrog.domain.BlueFrogResult
import com.amontdevs.bluefrog.repository.IAuthRepository
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.login.signin.SignInViewEvent
import com.amontdevs.bluefrog.ui.screens.login.signin.SignInViewModel.Companion.TAG
import com.amontdevs.bluefrog.util.IBluefrogLogger
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CreateAccountViewModel(
    private val authRepository: IAuthRepository,
    private val logger: IBluefrogLogger,
) : ViewModel() {

    private val _viewEvent = Channel<CreateAccountViewEvent>()
    val viewEvent = _viewEvent.receiveAsFlow()

    fun handleGoogleSignInResult(result: NativeSignInResult) {
        viewModelScope.launch {
            when (result) {
                NativeSignInResult.Success -> {
                    logger.d("Google Sign-In Successful!", tag = TAG)
                    getUserStatus()
                }
                NativeSignInResult.ClosedByUser -> {
                    // User closed the Google Sign-In prompt
                    logger.d("Google Sign-In cancelled by user.", tag = TAG)
                    // _startViewState.value = _startViewState.value.copy(isLoading = false)
                }
                is NativeSignInResult.Error -> {
                    // Handle general errors
                    logger.e("Google Sign-In failed: ${result.message}", tag = TAG)
                    // _startViewState.value = _startViewState.value.copy(isLoading = false)
                    // _eventFlow.emit(StartScreenEvent.ShowError("Google Sign-In failed: ${result.message}"))
                }
                is NativeSignInResult.NetworkError -> {
                    // Handle network-specific error
                    logger.e("Google Sign-In Network Error: ${result.message}", tag = TAG)
                    // _startViewState.value = _startViewState.value.copy(isLoading = false)
                    // _eventFlow.emit(StartScreenEvent.ShowError("Network error during Google Sign-In. Please try again."))
                }
            }
        }
    }

    private fun getUserStatus() {
        viewModelScope.launch {
            val result = authRepository.retrieveUserForCurrentSession()
            when (result) {
                is BlueFrogResult.Success -> {
                    logger.d("User is logged in! ${result.data}", tag = TAG)
                    _viewEvent.send(CreateAccountViewEvent.Navigate(LoginNavigation.Setup))
                }
                is BlueFrogResult.Error -> {
                    logger.d(result.exception.message.toString(), tag = TAG)
                }
            }
        }
    }
}