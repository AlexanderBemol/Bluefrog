package com.amontdevs.bluefrog.source.local

import com.amontdevs.bluefrog.domain.AbsoluteNote

expect interface INotesPlayer {
    fun playSound(bytes: ByteArray)
    fun stopPlaying()
    fun isPlaying()
}