package com.amontdevs.bluefrog.ui.screens.login.setup

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.amontdevs.bluefrog.ui.navigation.LoginNavigation
import com.amontdevs.bluefrog.ui.screens.common.BackButtonRow
import com.amontdevs.bluefrog.ui.theme.P3
import com.amontdevs.bluefrog.ui.theme.PrimaryButton
import com.amontdevs.bluefrog.ui.utils.FullScreenPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BasicSetupScreen(
    modifier: Modifier = Modifier,
    loginNavController: NavController,
    viewModel: BasicSetupViewModel = koinViewModel(),
) {
    BasicSetupScreen(
        modifier = modifier,
        stateFlow = viewModel.state,
        onPracticeItemClicked = viewModel::selectPracticeOption,
        onLevelItemClicked = viewModel::selectLevelOption,
        onDailyPracticeItemClicked = viewModel::selectDailyPracticeOption,
        onAgeItemClicked = viewModel::selectAgeOption,
        onBackClicked = {
            if (viewModel.state.value.currentScreen == BasicSetupViewState.BasicSetupScreen.SELECT_PRACTICE_TOPIC) {
                loginNavController.popBackStack()
            } else {
                viewModel.goBack()
            }
        },
        onContinueClicked = {
            if (viewModel.state.value.currentScreen == BasicSetupViewState.BasicSetupScreen.SELECT_AGE) {
                loginNavController.navigate(LoginNavigation.CreateAccount)
            } else {
                viewModel.goNext()
            }
        },
    )
}

@Composable
fun BasicSetupScreen(
    modifier: Modifier,
    stateFlow: StateFlow<BasicSetupViewState>,
    onPracticeItemClicked: (BasicSetupViewState.SelectPracticeTopic.Companion.OptionItem) -> Unit = {},
    onLevelItemClicked: (BasicSetupViewState.SelectLevel.Companion.OptionItem) -> Unit = {},
    onDailyPracticeItemClicked: (BasicSetupViewState.SelectDailyPractice.Companion.OptionItem) -> Unit = {},
    onAgeItemClicked: (BasicSetupViewState.SelectAge.Companion.OptionItem) -> Unit = {},
    onBackClicked: () -> Unit = {},
    onContinueClicked: () -> Unit = {},
) {
    val state = stateFlow.collectAsStateWithLifecycle()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        val animatedProgress by animateFloatAsState(
            targetValue = state.value.currentProgress,
            animationSpec = tween(durationMillis = 300, easing = EaseInOut),
            label = "Progress Bar Animation",
        )
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxWidth().height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primaryContainer,
        )

        BackButtonRow(
            onClick = onBackClicked,
        )

        AnimatedContent(
            targetState = state.value.currentScreen,
            transitionSpec = {
                val tweenDuration = 150
                val enter =
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }, // Start full width to the right
                        animationSpec = tween(durationMillis = tweenDuration, easing = LinearEasing), // Adjust timing/easing
                    ) + fadeIn(animationSpec = tween(100)) // Optional quick fade in

                val exit =
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }, // End full width to the left
                        animationSpec = tween(durationMillis = tweenDuration, easing = LinearEasing), // Adjust timing/easing
                    ) + fadeOut(animationSpec = tween(100)) // Optional quick fade out

                enter togetherWith exit
            },
            contentKey = { state -> state.ordinal },
        ) { targetState ->

            when (targetState) {
                BasicSetupViewState.BasicSetupScreen.SELECT_PRACTICE_TOPIC -> {
                    val screenState = state.value.selectPracticeTopicState
                    BasicSetupQuestionContent(
                        title = "What do you want to practice?",
                        items = screenState.options.map { it.first.optionName },
                        addCheckboxItems = true,
                        selectedIndexes = screenState.options.filter { it.second }.map { it.first.ordinal },
                        onItemSelected = { index ->
                            val option = screenState.options[index].first
                            onPracticeItemClicked(option)
                        },
                        isNextButtonEnabled = screenState.isNextButtonEnabled,
                        onContinueClicked = onContinueClicked,
                    )
                }

                BasicSetupViewState.BasicSetupScreen.SELECT_LEVEL -> {
                    val screenState = state.value.selectLevelState
                    BasicSetupQuestionContent(
                        title = "What level do you want to practice?",
                        items = screenState.options.map { it.optionName },
                        selectedIndexes = screenState.options.filter { it == screenState.selectedOption }.map { it.ordinal },
                        onItemSelected = { index ->
                            val option = screenState.options[index]
                            onLevelItemClicked(option)
                        },
                        isNextButtonEnabled = screenState.isNextButtonEnabled,
                        onContinueClicked = onContinueClicked,
                    )
                }

                BasicSetupViewState.BasicSetupScreen.SELECT_DAILY_PRACTICE -> {
                    val screenState = state.value.selectDailyPracticeState
                    BasicSetupQuestionContent(
                        title = "How much time do you want to practice every day?",
                        items = screenState.options.map { it.optionName },
                        selectedIndexes = screenState.options.filter { it == screenState.selectedOption }.map { it.ordinal },
                        onItemSelected = { index ->
                            val option = screenState.options[index]
                            onDailyPracticeItemClicked(option)
                        },
                        isNextButtonEnabled = screenState.isNextButtonEnabled,
                        onContinueClicked = onContinueClicked,
                    )
                }

                BasicSetupViewState.BasicSetupScreen.SELECT_AGE -> {
                    val screenState = state.value.selectAgeState
                    BasicSetupQuestionContent(
                        title = "What is your age?",
                        items = screenState.options.map { it.optionName },
                        selectedIndexes = screenState.options.filter { it == screenState.selectedOption }.map { it.ordinal },
                        onItemSelected = { index ->
                            val option = screenState.options[index]
                            onAgeItemClicked(option)
                        },
                        isNextButtonEnabled = screenState.isNextButtonEnabled,
                        onContinueClicked = onContinueClicked,
                    )
                }
            }
        }
    }
}

@Composable
fun BasicSetupQuestionContent(
    title: String,
    items: List<String>,
    addCheckboxItems: Boolean = false,
    selectedIndexes: List<Int> = emptyList(),
    onItemSelected: (index: Int) -> Unit = {},
    isNextButtonEnabled: Boolean = false,
    onContinueClicked: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(all = P3),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            fontSize = 28.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        LazyColumn {
            itemsIndexed(
                items = items,
                key = { i, _ -> i },
            ) { index, item ->
                SetupItemCard(
                    text = item,
                    modifier = Modifier.animateItem().padding(bottom = P3),
                    addCheckbox = addCheckboxItems,
                    isSelected = selectedIndexes.contains(index),
                    onItemSelected = {
                        onItemSelected(index)
                    },
                )
            }
        }

        PrimaryButton(
            text = "Continue",
            modifier = Modifier.fillMaxWidth(),
            onClick = onContinueClicked,
            enabled = isNextButtonEnabled,
        )
    }
}

@Composable
fun SetupItemCard(
    modifier: Modifier = Modifier,
    text: String,
    addCheckbox: Boolean = false,
    isSelected: Boolean = false,
    onItemSelected: () -> Unit = {},
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onBackground

    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        border =
            BorderStroke(
                1.dp,
                borderColor,
            ),
        onClick = onItemSelected,
        colors =
            CardDefaults.cardColors().copy(
                containerColor = backgroundColor,
                contentColor = contentColor,
            ),
    ) {
        Row(
            modifier = Modifier.padding(P3).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = text,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
            )
            if (addCheckbox) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { onItemSelected() },
                )
            }
        }
    }
}

@Composable
@Preview
private fun SetupScreenPreview() {
    FullScreenPreview(addPadding = false) {
        BasicSetupScreen(
            modifier = Modifier,
            stateFlow = MutableStateFlow(BasicSetupViewState()),
        )
    }
}
