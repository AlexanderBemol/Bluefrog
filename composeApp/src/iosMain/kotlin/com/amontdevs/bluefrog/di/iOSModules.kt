package com.amontdevs.bluefrog.di

import com.amontdevs.bluefrog.source.local.INotesPlayer
import com.amontdevs.bluefrog.source.local.NotesPlayer
import org.koin.dsl.module

private fun buildNotesPlayer(): INotesPlayer = NotesPlayer()

val iOSSourceModule = module {
    single { buildNotesPlayer() }
}