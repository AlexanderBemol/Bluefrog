package com.amontdevs.bluefrog.repository

import bluefrog.composeapp.generated.resources.Res
import com.amontdevs.bluefrog.domain.absolute.AbsoluteNote
import com.amontdevs.bluefrog.source.local.INotesPlayer
import org.jetbrains.compose.resources.ExperimentalResourceApi

interface IAudioRepository {
    suspend fun playSound(absoluteNote: AbsoluteNote)
}

class AudioRepository(
    private val notesPlayer: INotesPlayer,
) : IAudioRepository {
    @OptIn(ExperimentalResourceApi::class)
    override suspend fun playSound(absoluteNote: AbsoluteNote) {
        notesPlayer.playSound(
            Res.readBytes(absoluteNote.getRawFilePath()),
        )
    }
}
