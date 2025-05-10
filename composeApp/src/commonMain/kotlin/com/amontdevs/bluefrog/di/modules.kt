package com.amontdevs.bluefrog.di

import com.amontdevs.bluefrog.repository.AudioRepository
import com.amontdevs.bluefrog.repository.IAudioRepository
import com.amontdevs.bluefrog.source.local.INotesPlayer
import com.amontdevs.bluefrog.ui.screens.home.ManualModeViewModel
import com.amontdevs.bluefrog.ui.screens.session.absolute.AbsoluteSessionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModel { AbsoluteSessionViewModel(get()) }
        viewModel { ManualModeViewModel() }
    }

fun buildAudioRepository(notesPlayer: INotesPlayer): IAudioRepository = AudioRepository(notesPlayer)

val repositoryModule =
    module {
        factory { buildAudioRepository(get()) }
    }
