package com.amontdevs.bluefrog.ui.screens.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amontdevs.bluefrog.domain.BlueFrogResult
import com.amontdevs.bluefrog.repository.IAuthRepository
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.common.TextFieldState
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

class LoginViewModel(
    private val logger: IBluefrogLogger,
    private val authRepository: IAuthRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginViewState())
    val state = _state.asStateFlow()

    private val _navigationEvent = Channel<LoginNavigation>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun updateTexField(
        value: String,
        field: LoginTextFields,
    ) {
        when (field) {
            LoginTextFields.Email -> _state.value = _state.value.copy(email = TextFieldState(value))
            LoginTextFields.Password -> _state.value = _state.value.copy(password = TextFieldState(value))
        }
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
                            if (e.errorCode == AuthErrorCode.EmailNotConfirmed) {
                                _navigationEvent.send(LoginNavigation.ConfirmMail)
                            }
                        }
                    }
                }
                is BlueFrogResult.Success<UserInfo> -> {
                    logger.d("Login successful: ${result.data}", "LoginViewModel")
                    if (result.data.confirmedAt == null) {
                        _navigationEvent.send(LoginNavigation.ConfirmMail)
                    } else {
                        _navigationEvent.send(LoginNavigation.Setup)
                    }
                }
            }
        }
    }
}
