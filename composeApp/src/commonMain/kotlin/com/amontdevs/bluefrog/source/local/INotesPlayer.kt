package com.amontdevs.bluefrog.source.local

expect interface INotesPlayer {
    fun playSound(bytes: ByteArray)

    fun stopPlaying()

    fun isPlaying(): Boolean
}
