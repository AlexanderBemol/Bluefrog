package com.amontdevs.bluefrog.domain

import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.note_c
import bluefrog.composeapp.generated.resources.note_d
import bluefrog.composeapp.generated.resources.note_e
import org.jetbrains.compose.resources.DrawableResource

enum class AbsoluteNote(
    val audioFilename: String,
    val drawableResource: DrawableResource,
) {
    C3("piano_c3.mp3", Res.drawable.note_c),
    D3("piano_d3.mp3", Res.drawable.note_d),
    E3("piano_e3.mp3", Res.drawable.note_e),
    F3("f3.mp3", Res.drawable.note_c),
    G3("c3.mp3", Res.drawable.note_c),
    A3("c3.mp3", Res.drawable.note_c),
    B3("c3.mp3", Res.drawable.note_c),
    ;

    fun getRawFilePath() = "files/audio/${this.audioFilename}"

    override fun toString(): String =
        when (this) {
            C3 -> "Do"
            D3 -> "Re"
            E3 -> "Mi"
            F3 -> "Fa"
            G3 -> "Sol"
            A3 -> "La"
            B3 -> "Si"
        }
}
