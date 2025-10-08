package com.amontdevs.bluefrog.ui.screens.login.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amontdevs.bluefrog.domain.BlueFrogResult
import com.amontdevs.bluefrog.repository.IAuthRepository
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.util.IBluefrogLogger
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

class StartViewModel(
    private val logger: IBluefrogLogger,
    private val authRepository: IAuthRepository,
) : ViewModel() {
    private val _startViewState = MutableStateFlow(StartViewState())
    val startViewState = _startViewState.asStateFlow()

    private val _navigationEvent = Channel<LoginNavigation>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        getUserStatus()
    }

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
                    _startViewState.value = _startViewState.value.copy(isLoading = false)
                }

                is NativeSignInResult.Error -> {
                    // Handle general errors
                    logger.e("Google Sign-In failed: ${result.message}", tag = TAG)
                    _startViewState.value = _startViewState.value.copy(isLoading = false)
                    // _eventFlow.emit(StartScreenEvent.ShowError("Google Sign-In failed: ${result.message}"))
                }

                is NativeSignInResult.NetworkError -> {
                    // Handle network-specific error
                    logger.e("Google Sign-In Network Error: ${result.message}", tag = TAG)
                    _startViewState.value = _startViewState.value.copy(isLoading = false)
                    // _eventFlow.emit(StartScreenEvent.ShowError("Network error during Google Sign-In. Please try again."))
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun getUserStatus() {
        viewModelScope.launch {
            when (val result = authRepository.retrieveUserForCurrentSession()) {
                is BlueFrogResult.Success -> {
                    logger.d("User is logged in! ${result.data}", tag = TAG)
                    _navigationEvent.send(LoginNavigation.Setup)
                }

                is BlueFrogResult.Error -> {
                    logger.d(result.exception.message.toString(), tag = TAG)
                    val localUser = authRepository.getCurrentUser()
                    when (localUser) {
                        is BlueFrogResult.Success -> {
                            logger.d("Local User: ${localUser.data}", tag = TAG)
                            if (localUser.data.confirmedAt == null) {
                                _navigationEvent.send(LoginNavigation.ConfirmMail)
                            } else {
                                _navigationEvent.send(LoginNavigation.DebugLoginStatus)
                            }
                        }

                        is BlueFrogResult.Error -> {
                            logger.d(
                                "Error getting local user: ${localUser.exception.message}",
                                tag = TAG,
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "StartViewModel"
    }
}
