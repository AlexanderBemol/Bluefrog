package com.amontdevs.bluefrog.di

import com.amontdevs.bluefrog.source.local.INotesPlayer
import com.amontdevs.bluefrog.source.local.NotesPlayer
import com.amontdevs.bluefrog.util.BluefrogLogger
import com.amontdevs.bluefrog.util.IBluefrogLogger
import org.koin.dsl.module

private fun buildNotesPlayer(): INotesPlayer = NotesPlayer()

val desktopSourceModule =
    module {
        single { buildNotesPlayer() }
        single<IBluefrogLogger> { BluefrogLogger() }
    }
