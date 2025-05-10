package com.amontdevs.bluefrog.ui.screens.home

import androidx.lifecycle.ViewModel
import com.amontdevs.bluefrog.domain.AbsoluteNote
import kotlinx.coroutines.flow.MutableStateFlow

class ManualModeViewModel : ViewModel() {
    private val _manualModeState = MutableStateFlow(ManualModeState())
    val manualModeState = _manualModeState

    private val level1Notes = listOf(AbsoluteNote.C3, AbsoluteNote.D3, AbsoluteNote.E3)
    private val level2Notes = listOf(AbsoluteNote.F3, AbsoluteNote.G3, AbsoluteNote.A3)
    private val level3Notes = listOf(AbsoluteNote.B3, AbsoluteNote.CS3, AbsoluteNote.DS3)
    private val level4Notes = listOf(AbsoluteNote.FS3, AbsoluteNote.GS3, AbsoluteNote.AS3)
    private val level5Notes =
        listOf(
            AbsoluteNote.C4,
            AbsoluteNote.CS4,
            AbsoluteNote.D4,
            AbsoluteNote.DS4,
            AbsoluteNote.E4,
            AbsoluteNote.F4,
            AbsoluteNote.FS4,
            AbsoluteNote.G4,
            AbsoluteNote.GS4,
            AbsoluteNote.A4,
            AbsoluteNote.AS4,
            AbsoluteNote.B4,
        )

    init {
        _manualModeState.value =
            ManualModeState(
                sessions =
                    listOf(
                        CustomSession(
                            isPredefined = true,
                            title = "Level 1",
                            description = "Starts with C3, D3 and E3",
                            notes = level1Notes,
                        ),
                        CustomSession(
                            isPredefined = true,
                            title = "Level 2",
                            description = "Adds F3, G3 and A3 to the previous session",
                            notes = level1Notes + level2Notes,
                        ),
                        CustomSession(
                            isPredefined = true,
                            title = "Level 3",
                            description = "Adds B3, C#3 and D#3 to the previous session",
                            notes = level1Notes + level2Notes + level3Notes,
                        ),
                        CustomSession(
                            isPredefined = true,
                            title = "Level 4",
                            description = "Adds F#3, G#3 and A#3 to the previous session",
                            notes = level1Notes + level2Notes + level3Notes + level4Notes,
                        ),
                        CustomSession(
                            isPredefined = true,
                            title = "Level 5",
                            description = "Adds the octave 4 to the previous session",
                            notes = level1Notes + level2Notes + level3Notes + level4Notes + level5Notes,
                        ),
                    ),
            )
    }
}
