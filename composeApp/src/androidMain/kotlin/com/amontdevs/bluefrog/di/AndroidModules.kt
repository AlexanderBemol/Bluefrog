package com.amontdevs.bluefrog.di

import android.content.Context
import com.amontdevs.bluefrog.source.local.INotesPlayer
import com.amontdevs.bluefrog.source.local.NotesPlayer
import com.amontdevs.bluefrog.util.BluefrogLogger
import com.amontdevs.bluefrog.util.IBluefrogLogger
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private fun buildNotesPlayer(context: Context): INotesPlayer = NotesPlayer(context)
private fun buildLogger(): IBluefrogLogger = BluefrogLogger()

val androidSourceModule =
    module {
        single { buildNotesPlayer(androidContext()) }
        single { buildLogger() }
    }
