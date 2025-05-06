package com.amontdevs.bluefrog.source.local
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes

actual interface INotesPlayer {
    actual fun playSound(bytes: ByteArray)

    actual fun stopPlaying()

    actual fun isPlaying(): Boolean
}

class NotesPlayer : INotesPlayer {
    private var audioPlayer: AVAudioPlayer? = null

    @OptIn(ExperimentalForeignApi::class)
    override fun playSound(bytes: ByteArray) {
        stopPlaying()
        val data =
            bytes.usePinned { pinned ->
                NSData.dataWithBytes(
                    pinned.addressOf(0),
                    bytes.size.toULong(),
                )
            }
        audioPlayer = AVAudioPlayer(data, null)
        audioPlayer?.prepareToPlay()
        audioPlayer?.play()
    }

    override fun stopPlaying() {
        audioPlayer?.apply {
            if (playing) {
                stop()
            }
        }
    }

    override fun isPlaying() = audioPlayer?.playing ?: false
}
