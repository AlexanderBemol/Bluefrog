package com.amontdevs.bluefrog.ui.screens.login.signin

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: IAuthRepository,
    private val logger: IBluefrogLogger,
) : ViewModel() {
    private val _state = MutableStateFlow(SignInViewState())
    val state = _state.asStateFlow()
    private val _viewEvent = Channel<SignInViewEvent>()
    val viewEvent = _viewEvent.receiveAsFlow()

    fun updateTexField(
        value: String,
        field: SignInTextFields,
    ) {
        when (field) {
            SignInTextFields.Email -> {
                validateFields(email = value)
            }
            SignInTextFields.Password -> {
                validateFields(password = value)
            }
            SignInTextFields.ConfirmPassword -> {
                validateFields(confirmPassword = value)
            }
        }
    }

    private fun validateFields(
        email: String? = null,
        password: String? = null,
        confirmPassword: String? = null,
    ) {
        val emailValue = email ?: _state.value.email.value
        val passwordValue = password ?: _state.value.password.value
        val confirmPasswordValue = confirmPassword ?: _state.value.confirmPassword.value

        val emailError = if (!emailValue.contains("@")) "Invalid email" else null
        val passwordError =
            if (passwordValue.length < 8) {
                "Password must be at least 8 characters"
            } else if (passwordValue != confirmPasswordValue) {
                "Passwords do not match"
            } else {
                null
            }
        val confirmPasswordError =
            if (confirmPasswordValue.length < 8) {
                "Password must be at least 8 characters"
            } else if (passwordValue != confirmPasswordValue) {
                "Passwords do not match"
            } else {
                null
            }

        val signInButtonEnabled =
            emailError == null &&
                passwordError == null &&
                confirmPasswordError == null

        _state.value =
            _state.value.copy(
                email =
                    _state.value.email.copy(
                        value = emailValue,
                        error = emailError,
                    ),
                password =
                    _state.value.password.copy(
                        value = passwordValue,
                        error = passwordError,
                    ),
                confirmPassword =
                    _state.value.confirmPassword.copy(
                        value = confirmPasswordValue,
                        error = confirmPasswordError,
                    ),
                signInButtonEnabled = signInButtonEnabled,
            )
    }

    fun signIn() {
        viewModelScope.launch {
            if (_state.value.password != _state.value.confirmPassword) {
                _viewEvent.send(
                    SignInViewEvent.ShowToast(
                        CustomToast(
                            title = "Error",
                            message = "Passwords do not match",
                            kindOfToast = KindOfToast.Error,
                        ),
                    ),
                )
            } else {
                val result =
                    authRepository.signUp(
                        email = _state.value.email.value,
                        password = _state.value.confirmPassword.value,
                    )
                when (result) {
                    is BlueFrogResult.Success -> {
                        logger.d("Sign-up successful! ${result.data}", tag = TAG)
                        _viewEvent.send(SignInViewEvent.Navigate(LoginNavigation.ConfirmMail))
                    }
                    is BlueFrogResult.Error -> {
                        // Handle sign-up error
                        logger.e(result.exception.message.toString(), tag = TAG)
                        _viewEvent.send(
                            SignInViewEvent.ShowToast(
                                CustomToast(
                                    title = "Error",
                                    message = result.exception.message.toString(),
                                    kindOfToast = KindOfToast.Error,
                                ),
                            ),
                        )
                    }
                }
            }
        }
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
                    _viewEvent.send(SignInViewEvent.Navigate(LoginNavigation.Setup))
                }
                is BlueFrogResult.Error -> {
                    logger.d(result.exception.message.toString(), tag = TAG)
                }
            }
        }
    }

    companion object {
        const val TAG = "SignInViewModel"
    }
}
