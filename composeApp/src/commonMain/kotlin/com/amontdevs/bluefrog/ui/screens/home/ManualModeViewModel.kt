package com.amontdevs.bluefrog.ui.screens.home

import androidx.lifecycle.ViewModel
import com.amontdevs.bluefrog.domain.absolute.PredefinedAbsoluteSessions
import kotlinx.coroutines.flow.MutableStateFlow

class ManualModeViewModel : ViewModel() {
    private val _manualModeState = MutableStateFlow(ManualModeState())
    val manualModeState = _manualModeState

    init {
        _manualModeState.value =
            ManualModeState(
                sessions =
                    listOf(
                        PredefinedAbsoluteSessions.LEVEL_1.customSession,
                        PredefinedAbsoluteSessions.LEVEL_2.customSession,
                        PredefinedAbsoluteSessions.LEVEL_3.customSession,
                        PredefinedAbsoluteSessions.LEVEL_4.customSession,
                    ),
            )
    }
}
