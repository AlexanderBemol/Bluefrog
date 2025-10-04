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
                val strength = calculatePasswordStrength(value)
                validateFields(password = value, passwordStrength = strength)
                _state.value =
                    _state.value.copy(
                        password = _state.value.password.copy(value = value),
                        passwordStrength = strength,
                        passwordStrengthText =
                            when (strength) {
                                1 -> "Very Weak"
                                2 -> "Weak"
                                3 -> "Medium"
                                4 -> "Strong"
                                else -> "Enter a password to see strength."
                            },
                    )
            }
            SignInTextFields.ConfirmPassword -> {
                validateFields(confirmPassword = value)
            }
        }
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

    private fun validateFields(
        email: String? = null,
        password: String? = null,
        confirmPassword: String? = null,
        passwordStrength: Int? = null,
    ) {
        val emailValue = email ?: _state.value.email.value
        val passwordValue = password ?: _state.value.password.value
        val confirmPasswordValue = confirmPassword ?: _state.value.confirmPassword.value

        val emailError = if (!emailValue.contains("@")) "Invalid email" else null

        val confirmPasswordError =
            if (passwordValue != confirmPasswordValue) {
                "Passwords do not match"
            } else {
                null
            }

        val passwordError =
            passwordStrength?.let {
                if (it < 2) "Password is too weak" else null
            }

        val signInButtonEnabled =
            emailError == null &&
                confirmPasswordError == null

        _state.value =
            _state.value.copy(
                email =
                    _state.value.email.copy(
                        value = emailValue,
                        error = if (email != null) emailError else _state.value.email.error,
                    ),
                password =
                    _state.value.password.copy(
                        value = passwordValue,
                        error = if (password != null) passwordError else _state.value.password.error,
                    ),
                confirmPassword =
                    _state.value.confirmPassword.copy(
                        value = confirmPasswordValue,
                        error = if (confirmPassword != null) confirmPasswordError else _state.value.confirmPassword.error,
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

    private fun getUserStatus() {
        viewModelScope.launch {
            val result = authRepository.retrieveUserForCurrentSession()
            when (result) {
                is BlueFrogResult.Success -> {
                    logger.d("User is logged in! ${result.data}", tag = TAG)
                    _viewEvent.send(SignInViewEvent.Navigate(LoginNavigation.ConfirmMail))
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
