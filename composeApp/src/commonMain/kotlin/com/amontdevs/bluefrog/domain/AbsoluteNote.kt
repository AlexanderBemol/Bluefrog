package com.amontdevs.bluefrog.domain

import bluefrog.composeapp.generated.resources.Res
import bluefrog.composeapp.generated.resources.note_a3
import bluefrog.composeapp.generated.resources.note_a4
import bluefrog.composeapp.generated.resources.note_as3
import bluefrog.composeapp.generated.resources.note_as4
import bluefrog.composeapp.generated.resources.note_b3
import bluefrog.composeapp.generated.resources.note_b4
import bluefrog.composeapp.generated.resources.note_c3
import bluefrog.composeapp.generated.resources.note_c4
import bluefrog.composeapp.generated.resources.note_cs3
import bluefrog.composeapp.generated.resources.note_cs4
import bluefrog.composeapp.generated.resources.note_d3
import bluefrog.composeapp.generated.resources.note_d4
import bluefrog.composeapp.generated.resources.note_ds3
import bluefrog.composeapp.generated.resources.note_ds4
import bluefrog.composeapp.generated.resources.note_e3
import bluefrog.composeapp.generated.resources.note_e4
import bluefrog.composeapp.generated.resources.note_f3
import bluefrog.composeapp.generated.resources.note_f4
import bluefrog.composeapp.generated.resources.note_fs3
import bluefrog.composeapp.generated.resources.note_fs4
import bluefrog.composeapp.generated.resources.note_g3
import bluefrog.composeapp.generated.resources.note_g4
import bluefrog.composeapp.generated.resources.note_gs3
import bluefrog.composeapp.generated.resources.note_gs4
import org.jetbrains.compose.resources.DrawableResource

enum class AbsoluteNote(
    val audioFilename: String,
    val drawableResource: DrawableResource,
) {
    C3("piano_c3.mp3", Res.drawable.note_c3),
    CS3("piano_cs3.mp3", Res.drawable.note_cs3),
    D3("piano_d3.mp3", Res.drawable.note_d3),
    DS3("piano_ds3.mp3", Res.drawable.note_ds3),
    E3("piano_e3.mp3", Res.drawable.note_e3),
    F3("piano_f3.mp3", Res.drawable.note_f3),
    FS3("piano_fs3.mp3", Res.drawable.note_fs3),
    G3("piano_g3.mp3", Res.drawable.note_g3),
    GS3("piano_gs3.mp3", Res.drawable.note_gs3),
    A3("piano_a3.mp3", Res.drawable.note_a3),
    AS3("piano_as3.mp3", Res.drawable.note_as3),
    B3("piano_b3.mp3", Res.drawable.note_b3),
    C4("piano_c4.mp3", Res.drawable.note_c4),
    CS4("piano_cs4.mp3", Res.drawable.note_cs4),
    D4("piano_d4.mp3", Res.drawable.note_d4),
    DS4("piano_ds4.mp3", Res.drawable.note_ds4),
    E4("piano_e4.mp3", Res.drawable.note_e4),
    F4("piano_f4.mp3", Res.drawable.note_f4),
    FS4("piano_fs4.mp3", Res.drawable.note_fs4),
    G4("piano_g4.mp3", Res.drawable.note_g4),
    GS4("piano_gs4.mp3", Res.drawable.note_gs4),
    A4("piano_a4.mp3", Res.drawable.note_a4),
    AS4("piano_as4.mp3", Res.drawable.note_as4),
    B4("piano_b4.mp3", Res.drawable.note_b4),
    ;

    fun getRawFilePath() = "files/audio/${this.audioFilename}"

    override fun toString(): String =
        when (this) {
            C3 -> "Do"
            CS3 -> "Do#"
            D3 -> "Re"
            DS3 -> "Re#"
            E3 -> "Mi"
            F3 -> "Fa"
            FS3 -> "Fa#"
            G3 -> "Sol"
            GS3 -> "Sol#"
            A3 -> "La"
            AS3 -> "La#"
            B3 -> "Si"
            C4 -> "Do"
            CS4 -> "Do#"
            D4 -> "Re"
            DS4 -> "Re#"
            E4 -> "Mi"
            F4 -> "Fa"
            FS4 -> "Fa#"
            G4 -> "Sol"
            GS4 -> "Sol#"
            A4 -> "La"
            AS4 -> "La#"
            B4 -> "Si"
        }
}
