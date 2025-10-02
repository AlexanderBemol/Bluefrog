package com.amontdevs.bluefrog.di

import com.amontdevs.bluefrog.source.local.INotesPlayer
import com.amontdevs.bluefrog.source.local.NotesPlayer
import org.koin.dsl.module

val desktopSourceModule =
    module {
        factory<INotesPlayer> { NotesPlayer() }
    }
