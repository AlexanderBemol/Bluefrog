package com.amontdevs.bluefrog.ui.screens.login.setup

data class BasicSetupViewState(
    val currentProgress: Float = 0.0f,
    val currentScreen: BasicSetupScreen = BasicSetupScreen.SELECT_PRACTICE_TOPIC,
    val selectPracticeTopicState: SelectPracticeTopic = SelectPracticeTopic(),
    val selectLevelState: SelectLevel = SelectLevel(),
    val selectDailyPracticeState: SelectDailyPractice = SelectDailyPractice(),
    val selectAgeState: SelectAge = SelectAge(),
) {
    enum class BasicSetupScreen {
        SELECT_PRACTICE_TOPIC,
        SELECT_LEVEL,
        SELECT_DAILY_PRACTICE,
        SELECT_AGE,
    }

    data class SelectPracticeTopic(
        var options: List<Pair<OptionItem, Boolean>> =
            listOf(
                Pair(OptionItem.ABSOLUTE_NOTES, false),
                Pair(OptionItem.INTERVALS, false),
                Pair(OptionItem.CHORDS, false),
                Pair(OptionItem.SCALES, false),
            ),
        var isNextButtonEnabled: Boolean = false,
    ) {
        companion object {
            enum class OptionItem(
                val optionName: String,
            ) {
                ABSOLUTE_NOTES("Absolute Notes"),
                INTERVALS("Intervals"),
                CHORDS("Chords"),
                SCALES("Scales"),
            }
        }
    }

    data class SelectLevel(
        var options: List<OptionItem> = OptionItem.entries.toList(),
        var selectedOption: OptionItem? = null,
        var isNextButtonEnabled: Boolean = false,
    ) {
        companion object {
            enum class OptionItem(
                val optionName: String,
            ) {
                BEGINNER("I'm a beginner"),
                BASIC("I understand the basic concepts"),
                INTERMEDIATE("I can identify some notes"),
                ADVANCED("I can identify most notes easily"),
            }
        }
    }

    data class SelectDailyPractice(
        var options: List<OptionItem> = OptionItem.entries.toList(),
        var selectedOption: OptionItem? = null,
        var isNextButtonEnabled: Boolean = false,
    ) {
        companion object {
            enum class OptionItem(
                val optionName: String,
            ) {
                FIVE_MINUTES("5 minutes"),
                TEN_MINUTES("10 minutes"),
                TWENTY_MINUTES("20 minutes"),
                THIRTY_MINUTES("30 minutes"),
            }
        }
    }

    data class SelectAge(
        var options: List<OptionItem> = OptionItem.entries.toList(),
        var selectedOption: OptionItem? = null,
        var isNextButtonEnabled: Boolean = false,
    ) {
        companion object {
            enum class OptionItem(
                val optionName: String,
            ) {
                UNDER_18("Under 18"),
                AGE_18_24("18-24"),
                AGE_25_34("25-34"),
                AGE_35_44("35-44"),
                AGE_45_54("45-54"),
                AGE_55_PLUS("55+"),
            }
        }
    }
}
