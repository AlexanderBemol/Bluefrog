package com.amontdevs.bluefrog.ui.screens.login.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amontdevs.bluefrog.domain.BlueFrogResult
import com.amontdevs.bluefrog.repository.IAuthRepository
import com.amontdevs.bluefrog.util.IBluefrogLogger
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

class LoginStatusViewModel(
    private val authRepository: IAuthRepository,
    private val logger: IBluefrogLogger,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginStatusStateView())
    val state: StateFlow<LoginStatusStateView> = _state.asStateFlow()

    init {
        getCurrentUser()
    }

    @OptIn(ExperimentalTime::class)
    fun getCurrentUser() {
        val result = authRepository.getCurrentUser()
        when (result) {
            is BlueFrogResult.Error ->
                logger.d(
                    tag = TAG,
                    message = result.exception.message.toString(),
                )

            is BlueFrogResult.Success<UserInfo> -> {
                logger.d(tag = TAG, message = "Success: ${result.data}")
                _state.value =
                    _state.value.copy(
                        aud = result.data.aud,
                        confirmationSentAt = result.data.confirmationSentAt.toString(),
                        confirmedAt = result.data.confirmedAt.toString(),
                        createdAt = result.data.createdAt.toString(),
                        email = result.data.email.toString(),
                        emailConfirmedAt = result.data.emailConfirmedAt.toString(),
                        userMetadata = result.data.userMetadata.toString(),
                    )
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun retrieveUserForCurrentSession() {
        viewModelScope.launch {
            val result = authRepository.retrieveUserForCurrentSession()
            when (result) {
                is BlueFrogResult.Error ->
                    logger.d(
                        tag = TAG,
                        message = result.exception.message.toString(),
                    )

                is BlueFrogResult.Success<UserInfo> -> {
                    logger.d(tag = TAG, message = "Success: ${result.data}")
                    _state.value =
                        _state.value.copy(
                            aud = result.data.aud,
                            confirmationSentAt = result.data.confirmationSentAt.toString(),
                            confirmedAt = result.data.confirmedAt.toString(),
                            createdAt = result.data.createdAt.toString(),
                            email = result.data.email.toString(),
                            emailConfirmedAt = result.data.emailConfirmedAt.toString(),
                            userMetadata = result.data.userMetadata.toString(),
                        )
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            val result = authRepository.signOut()
            when (result) {
                is BlueFrogResult.Error ->
                    logger.d(
                        tag = TAG,
                        message = result.exception.message.toString(),
                    )

                is BlueFrogResult.Success<Unit> -> {
                    logger.d(tag = TAG, message = "Success: ${result.data}")
                }
            }
        }
    }

    companion object {
        private const val TAG = "LoginStatusViewModel"
    }
}
