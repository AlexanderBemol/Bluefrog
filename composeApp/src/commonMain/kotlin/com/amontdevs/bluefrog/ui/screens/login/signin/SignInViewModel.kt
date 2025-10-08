package com.amontdevs.bluefrog.ui.screens.login.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amontdevs.bluefrog.domain.BlueFrogResult
import com.amontdevs.bluefrog.repository.IAuthRepository
import com.amontdevs.bluefrog.ui.dialog.CustomToast
import com.amontdevs.bluefrog.ui.dialog.KindOfToast
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.common.TextFieldState
import com.amontdevs.bluefrog.util.IBluefrogLogger
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
                onEmailChanged(email = value)
            }
            SignInTextFields.Password -> {
                onPasswordChanged(password = value)
            }
            SignInTextFields.ConfirmPassword -> {
                onConfirmPasswordChanged(confirmPassword = value)
            }
        }
    }

    private fun onEmailChanged(email: String) {
        val emailError = if (!email.contains("@")) "Invalid email" else null
        val isSignInButtonEnabled =
            emailError == null &&
                _state.value.password.error == null &&
                _state.value.confirmPassword.error == null

        _state.value =
            _state.value.copy(
                email =
                    TextFieldState(
                        value = email,
                        error = emailError,
                    ),
                signInButtonEnabled = isSignInButtonEnabled,
            )
    }

    private fun onPasswordChanged(password: String) {
        val passwordStrength = calculatePasswordStrength(password)
        val passwordError = if (passwordStrength < 2) "Password is too weak" else null
        val confirmPasswordError =
            if (password != _state.value.confirmPassword.value) {
                "Passwords do not match"
            } else {
                null
            }

        val isSignInButtonEnabled =
            _state.value.email.error == null &&
                passwordError == null &&
                confirmPasswordError == null

        _state.value =
            _state.value.copy(
                password =
                    TextFieldState(
                        value = password,
                        error = passwordError,
                    ),
                confirmPassword =
                    TextFieldState(
                        value = _state.value.confirmPassword.value,
                        error = confirmPasswordError,
                    ),
                passwordStrength = passwordStrength,
                passwordStrengthText =
                    when (passwordStrength) {
                        1 -> "Very Weak"
                        2 -> "Weak"
                        3 -> "Medium"
                        4 -> "Strong"
                        else -> "Enter a password to see strength."
                    },
                signInButtonEnabled = isSignInButtonEnabled,
            )
    }

    private fun onConfirmPasswordChanged(confirmPassword: String) {
        val confirmPasswordError =
            if (confirmPassword != _state.value.password.value) {
                "Passwords do not match"
            } else {
                null
            }

        val isSignInButtonEnabled =
            _state.value.email.error == null &&
                _state.value.password.error == null &&
                confirmPasswordError == null

        _state.value =
            _state.value.copy(
                confirmPassword =
                    TextFieldState(
                        value = confirmPassword,
                        error = confirmPasswordError,
                    ),
                signInButtonEnabled = isSignInButtonEnabled,
            )
    }

    fun changePasswordVisibility() {
        _state.value = _state.value.copy(showPassword = !_state.value.showPassword)
    }

    fun changeConfirmPasswordVisibility() {
        _state.value = _state.value.copy(showConfirmPassword = !_state.value.showConfirmPassword)
    }

    private fun calculatePasswordStrength(password: String): Int {
        if (password.isEmpty()) {
            return 0 // Level 0: Empty
        }

        val length = password.length

        val hasLowercase = password.any { it.isLowerCase() }
        val hasUppercase = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val specialChars = "!@#\$%^&*()_+-=[]{}|;':\",./<>?"
        val hasSpecial = password.any { specialChars.contains(it) }

        var charTypes = 0
        if (hasLowercase) charTypes++
        if (hasUppercase) charTypes++
        if (hasDigit) charTypes++
        if (hasSpecial) charTypes++

        // Level 4: Strong
        // Password length is 12 or more characters.
        // AND contains all four character types (lowercase, uppercase, numbers, and special characters).
        if (length >= 12 && charTypes == 4) {
            return 4
        }

        // Level 3: Medium
        // Password length is 10 or more characters.
        // AND contains at least three different character types.
        if (length >= 10 && charTypes >= 3) {
            return 3
        }

        // Level 2: Acceptable
        // Password length is 8 or more characters.
        // AND contains at least two different character types.
        if (length >= 8 && charTypes >= 2) {
            return 2
        }

        // Level 1: Very Weak
        // Covers:
        // - Password length is less than 8 characters.
        // - OR password length is 8 or more characters but contains only one type of character.
        return 1
    }

    fun signIn() {
        viewModelScope.launch {
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

    companion object {
        const val TAG = "SignInViewModel"
    }
}
