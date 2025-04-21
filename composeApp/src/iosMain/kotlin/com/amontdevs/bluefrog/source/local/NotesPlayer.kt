package com.amontdevs.bluefrog.source.local

actual interface INotesPlayer {
    actual fun playSound(bytes: ByteArray)
    actual fun stopPlaying()
    actual fun isPlaying()
}

class NotesPlayer: INotesPlayer {
    override fun playSound(bytes: ByteArray) {
        TODO("Not yet implemented")
    }

    override fun stopPlaying() {
        TODO("Not yet implemented")
    }

    override fun isPlaying() {
        TODO("Not yet implemented")
    }
}