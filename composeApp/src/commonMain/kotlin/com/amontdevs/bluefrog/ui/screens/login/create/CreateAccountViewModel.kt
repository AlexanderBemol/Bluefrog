package com.amontdevs.bluefrog.ui.screens.login.create

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
                    logger.d("Google Sign-In cancelled by user.", tag = TAG)
                }

                is NativeSignInResult.Error -> {
                    logger.e("Google Sign-In failed: ${result.message}", tag = TAG)
                    _viewEvent.send(
                        CreateAccountViewEvent.ShowToast(
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
                    _viewEvent.send(CreateAccountViewEvent.Navigate(LoginNavigation.DebugLoginStatus))
                }

                is BlueFrogResult.Error -> {
                    _viewEvent.send(
                        CreateAccountViewEvent.ShowToast(
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
        private const val TAG = "CreateAccountViewModel"
    }
}
