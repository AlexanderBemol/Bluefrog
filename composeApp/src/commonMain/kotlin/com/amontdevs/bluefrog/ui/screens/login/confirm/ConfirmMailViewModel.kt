package com.amontdevs.bluefrog.ui.screens.login.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amontdevs.bluefrog.domain.BlueFrogResult
import com.amontdevs.bluefrog.repository.AuthRepository.Companion.TAG
import com.amontdevs.bluefrog.repository.IAuthRepository
import com.amontdevs.bluefrog.util.IBluefrogLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConfirmMailViewModel(
    private val authRepository: IAuthRepository,
    private val logger: IBluefrogLogger,
) : ViewModel() {
    private val _confirmMailViewState = MutableStateFlow(ConfirmMailViewState())
    val confirmMailViewState = _confirmMailViewState.asStateFlow()

    init {
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            when (val result = authRepository.getCurrentUser()) {
                is BlueFrogResult.Success -> {
                    logger.d("User is logged in! ${result.data}", tag = TAG)
                    _confirmMailViewState.value =
                        _confirmMailViewState.value.copy(
                            currentMail = result.data.email ?: "",
                        )
                }
                is BlueFrogResult.Error -> {
                    logger.e("User is not logged in!", tag = TAG)
                }
            }
        }
    }
}
