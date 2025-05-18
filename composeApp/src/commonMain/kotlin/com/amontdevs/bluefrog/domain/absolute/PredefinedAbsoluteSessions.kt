package com.amontdevs.bluefrog.domain.absolute

enum class PredefinedAbsoluteSessions(
    val customSession: CustomSession,
) {
    LEVEL_1(
        CustomSession(
            id = 1,
            isPredefined = true,
            title = "Level 1",
            description = "Start with C3, D3 and E3.",
            notes =
                listOf(
                    SessionAbsoluteNote(
                        absoluteNote = AbsoluteNote.C3,
                        isSessionMainNote = true,
                    ),
                    SessionAbsoluteNote(
                        absoluteNote = AbsoluteNote.D3,
                        isSessionMainNote = true,
                    ),
                    SessionAbsoluteNote(
                        absoluteNote = AbsoluteNote.E3,
                        isSessionMainNote = true,
                    ),
                ),
        ),
    ),
    LEVEL_2(
        CustomSession(
            id = 2,
            isPredefined = true,
            title = "Level 2",
            description = "Adds F3, G3 and A3 to the previous level.",
            notes =
                listOf(
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.C3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.D3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.E3),
                    SessionAbsoluteNote(
                        absoluteNote = AbsoluteNote.F3,
                        isSessionMainNote = true,
                    ),
                    SessionAbsoluteNote(
                        absoluteNote = AbsoluteNote.G3,
                        isSessionMainNote = true,
                    ),
                    SessionAbsoluteNote(
                        absoluteNote = AbsoluteNote.A3,
                        isSessionMainNote = true,
                    ),
                ),
        ),
    ),
    LEVEL_3(
        CustomSession(
            id = 3,
            isPredefined = true,
            title = "Level 3",
            description = "Adds B3, C#3 and D#3 to the previous level.",
            notes =
                listOf(
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.C3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.D3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.E3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.F3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.G3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.A3),
                    SessionAbsoluteNote(
                        absoluteNote = AbsoluteNote.B3,
                        isSessionMainNote = true,
                    ),
                    SessionAbsoluteNote(
                        absoluteNote = AbsoluteNote.CS3,
                        isSessionMainNote = true,
                    ),
                    SessionAbsoluteNote(
                        absoluteNote = AbsoluteNote.DS3,
                        isSessionMainNote = true,
                    ),
                ),
        ),
    ),
    LEVEL_4(
        CustomSession(
            id = 4,
            isPredefined = true,
            title = "Level 4",
            description = "Adds F#3, G#3 and A#3 to the previous level.",
            notes =
                listOf(
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.C3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.D3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.E3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.F3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.G3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.A3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.B3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.CS3),
                    SessionAbsoluteNote(absoluteNote = AbsoluteNote.DS3),
                    SessionAbsoluteNote(
                        absoluteNote = AbsoluteNote.FS3,
                        isSessionMainNote = true,
                    ),
                    SessionAbsoluteNote(
                        absoluteNote = AbsoluteNote.GS3,
                        isSessionMainNote = true,
                    ),
                    SessionAbsoluteNote(
                        absoluteNote = AbsoluteNote.AS3,
                        isSessionMainNote = true,
                    ),
                ),
        ),
    ),
}
