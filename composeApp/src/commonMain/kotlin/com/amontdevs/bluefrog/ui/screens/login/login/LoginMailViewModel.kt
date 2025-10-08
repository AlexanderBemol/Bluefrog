package com.amontdevs.bluefrog.ui.screens.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amontdevs.bluefrog.domain.BlueFrogResult
import com.amontdevs.bluefrog.repository.IAuthRepository
import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.dialog.KindOfToast
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.util.IBluefrogLogger
import io.github.jan.supabase.auth.exception.AuthErrorCode
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

class LoginMailViewModel(
    private val logger: IBluefrogLogger,
    private val authRepository: IAuthRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginMailViewState())
    val state = _state.asStateFlow()

    private val _viewEvent = Channel<LoginMailViewEvent>()
    val viewEvent = _viewEvent.receiveAsFlow()

    fun updateTexField(
        value: String,
        field: LoginMailTextFields,
    ) {
        when (field) {
            LoginMailTextFields.Email -> updateEmail(value)
            LoginMailTextFields.Password -> updatePassword(value)
        }
    }

    fun toggleShowPassword() {
        _state.value = _state.value.copy(showPassword = !_state.value.showPassword)
    }

    private fun updateEmail(value: String) {
        val emailError =
            if (value.isBlank()) {
                "Email cannot be blank"
            } else if (!value.contains("@")) {
                "Invalid email format"
            } else {
                null
            }

        val isLoginButtonEnabled = emailError == null && _state.value.password.error == null

        _state.value =
            _state.value.copy(
                email = _state.value.email.copy(value = value, error = emailError),
                isLoginButtonEnabled = isLoginButtonEnabled,
            )
    }

    private fun updatePassword(value: String) {
        val passwordError = if (value.isBlank()) "Password cannot be blank" else null

        val isLoginButtonEnabled = passwordError == null && _state.value.email.error == null

        _state.value =
            _state.value.copy(
                password = _state.value.password.copy(value = value, error = passwordError),
                isLoginButtonEnabled = isLoginButtonEnabled,
            )
    }

    @OptIn(ExperimentalTime::class)
    fun login() {
        viewModelScope.launch {
            val result =
                authRepository.login(
                    email = state.value.email.value,
                    password = state.value.password.value,
                )
            when (result) {
                is BlueFrogResult.Error -> {
                    logger.e(result.exception.message.toString(), "LoginViewModel")
                    when (val e = result.exception) {
                        is AuthRestException -> {
                            if (e.errorCode == AuthErrorCode.InvalidCredentials || e.errorCode == AuthErrorCode.UserNotFound) {
                                _viewEvent.send(
                                    LoginMailViewEvent.ShowToast(
                                        CustomToast(
                                            title = "Invalid credentials",
                                            message = "Please check your email and password",
                                            kindOfToast = KindOfToast.Error,
                                        ),
                                    ),
                                )
                            } else if (e.errorCode == AuthErrorCode.EmailNotConfirmed) {
                                _viewEvent.send(
                                    LoginMailViewEvent.Navigate(
                                        LoginNavigation.ConfirmMail,
                                    ),
                                )
                            } else {
                                _viewEvent.send(
                                    LoginMailViewEvent.ShowToast(
                                        CustomToast(
                                            title = "Error",
                                            message = "Something went wrong. Please try again later.",
                                            kindOfToast = KindOfToast.Error,
                                        ),
                                    ),
                                )
                            }
                        }
                    }
                }

                is BlueFrogResult.Success<UserInfo> -> {
                    logger.d("Login successful: ${result.data}", "LoginViewModel")
                    if (result.data.confirmedAt == null) {
                        _viewEvent.send(
                            LoginMailViewEvent.Navigate(
                                LoginNavigation.ConfirmMail,
                            ),
                        )
                    } else {
                        _viewEvent.send(
                            LoginMailViewEvent.Navigate(
                                LoginNavigation.DebugLoginStatus,
                            ),
                        )
                    }
                }
            }
        }
    }
}
