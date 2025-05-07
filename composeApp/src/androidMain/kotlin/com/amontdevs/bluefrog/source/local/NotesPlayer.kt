package com.amontdevs.bluefrog.source.local

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.core.net.toUri
import org.jetbrains.compose.resources.ExperimentalResourceApi
import java.io.File

actual interface INotesPlayer {
    actual fun playSound(bytes: ByteArray)

    actual fun stopPlaying()

    actual fun isPlaying(): Boolean
}

class NotesPlayer(
    private val context: Context,
) : INotesPlayer {
    private var mediaPlayer: MediaPlayer? = null

    @OptIn(ExperimentalResourceApi::class)
    override fun playSound(bytes: ByteArray) {
        try {
            val tempFile = File.createTempFile("audio", ".mp3", context.cacheDir)
            tempFile.writeBytes(bytes)
            stopPlaying()
            mediaPlayer =
                MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes
                            .Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build(),
                    )
                    setDataSource(context, tempFile.toUri())
                    prepare()
                    start()
                }

            mediaPlayer?.setOnCompletionListener {
                stopPlaying()
            }
        } catch (e: Exception) {
            stopPlaying()
            Log.d("NotesPlayer", e.toString())
        }
    }

    override fun stopPlaying() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
    }

    override fun isPlaying() = mediaPlayer?.isPlaying ?: false
}
