package com.amontdevs.bluefrog.ui.screens.login.restore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amontdevs.bluefrog.domain.BlueFrogResult
import com.amontdevs.bluefrog.repository.IAuthRepository
import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.dialog.KindOfToast
import com.amontdevs.bluefrog.util.IBluefrogLogger
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RestorePasswordViewModel(
    private val authRepository: IAuthRepository,
    private val logger: IBluefrogLogger,
) : ViewModel() {
    private val _state = MutableStateFlow(RestorePasswordViewState())
    val state: StateFlow<RestorePasswordViewState> = _state.asStateFlow()

    private val _viewEvent = Channel<RestorePasswordViewEvent>()
    val viewEvent = _viewEvent.receiveAsFlow()

    fun updateEmail(value: String) {
        val error =
            if (value.isEmpty()) {
                "Email is required"
            } else if (!value.contains("@")) {
                "Email is invalid"
            } else {
                null
            }
        _state.value =
            _state.value.copy(
                email = _state.value.email.copy(value = value, error = error),
                isRestoreButtonEnabled = error == null,
            )
    }

    fun resetPassword() {
        viewModelScope.launch {
            val result = authRepository.resetPassword(_state.value.email.value)
            when (result) {
                is BlueFrogResult.Error -> {
                    logger.e(result.exception.message.toString(), TAG)
                    _viewEvent.send(
                        RestorePasswordViewEvent.ShowToast(
                            CustomToast(
                                message = "Error resetting password",
                                kindOfToast = KindOfToast.Error,
                            ),
                        ),
                    )
                }

                is BlueFrogResult.Success<Unit> -> {
                    logger.d("Password reset email sent", TAG)
                    _viewEvent.send(
                        RestorePasswordViewEvent.ShowToast(
                            CustomToast(
                                title = "Password reset email sent",
                                message = "Please check your email",
                                kindOfToast = KindOfToast.Success,
                            ),
                        ),
                    )
                }
            }
        }
    }

    companion object {
        const val TAG = "RestorePasswordViewModel"
    }
}
