package com.amontdevs.bluefrog.ui.screens.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amontdevs.bluefrog.domain.BlueFrogResult
import com.amontdevs.bluefrog.repository.IAuthRepository
import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.dialog.KindOfToast
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.util.IBluefrogLogger
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val logger: IBluefrogLogger,
    private val authRepository: IAuthRepository,
) : ViewModel() {
    private val _viewEvent = Channel<LoginViewEvent>()
    val viewEvent = _viewEvent.receiveAsFlow()

    fun handleGoogleSignInResult(result: NativeSignInResult) {
        viewModelScope.launch {
            when (result) {
                NativeSignInResult.Success -> {
                    logger.d("Google Sign-In Successful!", tag = TAG)
                    getUserStatus()
                }

                NativeSignInResult.ClosedByUser -> {
                    logger.d("Google Sign-In cancelled by user.", tag = TAG)
                }

                is NativeSignInResult.Error -> {
                    logger.e("Google Sign-In failed: ${result.message}", tag = TAG)
                    _viewEvent.send(
                        LoginViewEvent.ShowToast(
                            CustomToast(
                                message = "Google Sign-In failed",
                                kindOfToast = KindOfToast.Error,
                            ),
                        ),
                    )
                }

                is NativeSignInResult.NetworkError -> {
                    logger.e("Network Error", tag = TAG)
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

                    _viewEvent.send(LoginViewEvent.Navigate(LoginNavigation.DebugLoginStatus))
                }

                is BlueFrogResult.Error -> {
                    _viewEvent.send(
                        LoginViewEvent.ShowToast(
                            CustomToast(
                                message = "Error getting user status",
                                kindOfToast = KindOfToast.Error,
                            ),
                        ),
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}
