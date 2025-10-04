package com.amontdevs.bluefrog.ui.screens.login.setup

import androidx.lifecycle.ViewModel
import com.amontdevs.bluefrog.ui.screens.login.setup.BasicSetupViewState.BasicSetupScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BasicSetupViewModel : ViewModel() {
    private val _state = MutableStateFlow(BasicSetupViewState())
    val state = _state.asStateFlow()

    fun selectPracticeOption(option: BasicSetupViewState.SelectPracticeTopic.Companion.OptionItem) {
        val selectedOptions = _state.value.selectPracticeTopicState.options
        val updatedOptions =
            selectedOptions.map {
                if (it.first == option) {
                    Pair(it.first, !it.second)
                } else {
                    it
                }
            }
        _state.value =
            _state.value.copy(
                selectPracticeTopicState =
                    BasicSetupViewState.SelectPracticeTopic(
                        options = updatedOptions,
                        isNextButtonEnabled = updatedOptions.any { it.second },
                    ),
            )
    }

    fun selectLevelOption(option: BasicSetupViewState.SelectLevel.Companion.OptionItem) {
        val currentSelectedOption = _state.value.selectLevelState.selectedOption
        val newSelectedOption = if (option != currentSelectedOption) option else null

        _state.value =
            _state.value.copy(
                selectLevelState =
                    BasicSetupViewState.SelectLevel(
                        selectedOption = newSelectedOption,
                        isNextButtonEnabled = newSelectedOption != null,
                    ),
            )
    }

    fun selectDailyPracticeOption(option: BasicSetupViewState.SelectDailyPractice.Companion.OptionItem) {
        val currentSelectedOption = _state.value.selectDailyPracticeState.selectedOption
        val newSelectedOption = if (option != currentSelectedOption) option else null

        _state.value =
            _state.value.copy(
                selectDailyPracticeState =
                    BasicSetupViewState.SelectDailyPractice(
                        selectedOption = newSelectedOption,
                        isNextButtonEnabled = newSelectedOption != null,
                    ),
            )
    }

    fun selectAgeOption(option: BasicSetupViewState.SelectAge.Companion.OptionItem) {
        val currentSelectedOption = _state.value.selectAgeState.selectedOption
        val newSelectedOption = if (option != currentSelectedOption) option else null

        _state.value =
            _state.value.copy(
                selectAgeState =
                    BasicSetupViewState.SelectAge(
                        selectedOption = newSelectedOption,
                        isNextButtonEnabled = newSelectedOption != null,
                    ),
            )
    }

    fun goNext() {
        when (_state.value.currentScreen) {
            BasicSetupScreen.SELECT_PRACTICE_TOPIC -> {
                _state.value =
                    _state.value.copy(
                        currentScreen = BasicSetupScreen.SELECT_LEVEL,
                        currentProgress = .25f,
                    )
            }

            BasicSetupScreen.SELECT_LEVEL -> {
                _state.value =
                    _state.value.copy(
                        currentScreen = BasicSetupScreen.SELECT_DAILY_PRACTICE,
                        currentProgress = .5f,
                    )
            }

            BasicSetupScreen.SELECT_DAILY_PRACTICE -> {
                _state.value =
                    _state.value.copy(
                        currentScreen = BasicSetupScreen.SELECT_AGE,
                        currentProgress = .75f,
                    )
            }

            else -> {}
        }
    }

    fun goBack() {
        when (_state.value.currentScreen) {
            BasicSetupScreen.SELECT_AGE -> {
                _state.value =
                    _state.value.copy(
                        currentScreen = BasicSetupScreen.SELECT_DAILY_PRACTICE,
                        currentProgress = .5f,
                    )
            }

            BasicSetupScreen.SELECT_DAILY_PRACTICE -> {
                _state.value =
                    _state.value.copy(
                        currentScreen = BasicSetupScreen.SELECT_LEVEL,
                        currentProgress = .25f,
                    )
            }

            BasicSetupScreen.SELECT_LEVEL -> {
                _state.value =
                    _state.value.copy(
                        currentScreen = BasicSetupScreen.SELECT_PRACTICE_TOPIC,
                        currentProgress = 0f,
                    )
            }

            else -> {}
        }
    }
}
