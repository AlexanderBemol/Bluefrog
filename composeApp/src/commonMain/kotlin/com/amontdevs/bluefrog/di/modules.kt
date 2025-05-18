package com.amontdevs.bluefrog.di

import com.amontdevs.bluefrog.repository.AbsoluteSessionRepository
import com.amontdevs.bluefrog.repository.AudioRepository
import com.amontdevs.bluefrog.repository.IAbsoluteSessionRepository
import com.amontdevs.bluefrog.repository.IAudioRepository
import com.amontdevs.bluefrog.source.local.INotesPlayer
import com.amontdevs.bluefrog.ui.screens.home.ManualModeViewModel
import com.amontdevs.bluefrog.ui.screens.session.absolute.AbsoluteSessionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModel { params ->
            AbsoluteSessionViewModel(
                get(),
                get { parametersOf(params.get<Int>()) },
            )
        }
        viewModel { ManualModeViewModel() }
    }

fun buildAudioRepository(notesPlayer: INotesPlayer): IAudioRepository = AudioRepository(notesPlayer)

fun buildAbsoluteSessionRepository(sessionId: Int): IAbsoluteSessionRepository = AbsoluteSessionRepository(sessionId)

val repositoryModule =
    module {
        factory { buildAudioRepository(get()) }
        factory { params -> buildAbsoluteSessionRepository(params.get()) }
    }
