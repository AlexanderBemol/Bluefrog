package com.amontdevs.bluefrog.ui.screens.session.absolute.match

import com.amontdevs.bluefrog.ui.screens.session.absolute.AnswerState
import com.amontdevs.bluefrog.ui.screens.session.absolute.NoteOption

data class AbsoluteMatchState(
    val answerState: AnswerState = AnswerState.NotAnsweredYet,
    val options: List<NoteOption> = listOf(),
)
