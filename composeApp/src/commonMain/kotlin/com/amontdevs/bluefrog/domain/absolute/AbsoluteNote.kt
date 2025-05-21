package com.amontdevs.bluefrog.domain.absolute

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
    val code: Int,
    val audioFilename: String,
    val drawableResource: DrawableResource,
) {
    C3(36, "piano_c3.mp3", Res.drawable.note_c3),
    CS3(37, "piano_cs3.mp3", Res.drawable.note_cs3),
    D3(38, "piano_d3.mp3", Res.drawable.note_d3),
    DS3(39, "piano_ds3.mp3", Res.drawable.note_ds3),
    E3(40, "piano_e3.mp3", Res.drawable.note_e3),
    F3(41, "piano_f3.mp3", Res.drawable.note_f3),
    FS3(42, "piano_fs3.mp3", Res.drawable.note_fs3),
    G3(43, "piano_g3.mp3", Res.drawable.note_g3),
    GS3(44, "piano_gs3.mp3", Res.drawable.note_gs3),
    A3(45, "piano_a3.mp3", Res.drawable.note_a3),
    AS3(46, "piano_as3.mp3", Res.drawable.note_as3),
    B3(47, "piano_b3.mp3", Res.drawable.note_b3),
    C4(48, "piano_c4.mp3", Res.drawable.note_c4),
    CS4(49, "piano_cs4.mp3", Res.drawable.note_cs4),
    D4(50, "piano_d4.mp3", Res.drawable.note_d4),
    DS4(51, "piano_ds4.mp3", Res.drawable.note_ds4),
    E4(52, "piano_e4.mp3", Res.drawable.note_e4),
    F4(53, "piano_f4.mp3", Res.drawable.note_f4),
    FS4(54, "piano_fs4.mp3", Res.drawable.note_fs4),
    G4(55, "piano_g4.mp3", Res.drawable.note_g4),
    GS4(56, "piano_gs4.mp3", Res.drawable.note_gs4),
    A4(57, "piano_a4.mp3", Res.drawable.note_a4),
    AS4(58, "piano_as4.mp3", Res.drawable.note_as4),
    B4(59, "piano_b4.mp3", Res.drawable.note_b4),
    ;

    fun getRawFilePath() = "files/audio/${this.audioFilename}"

    fun getFromCode(code: Int): AbsoluteNote = entries.firstOrNull { it.code == code } ?: C3

    override fun toString(): String =
        when (this) {
            C3 -> "Do3"
            CS3 -> "Do#3"
            D3 -> "Re3"
            DS3 -> "Re#3"
            E3 -> "Mi3"
            F3 -> "Fa3"
            FS3 -> "Fa#3"
            G3 -> "Sol3"
            GS3 -> "Sol3"
            A3 -> "La3"
            AS3 -> "La3"
            B3 -> "Si3"
            C4 -> "Do4"
            CS4 -> "Do#4"
            D4 -> "Re4"
            DS4 -> "Re#4"
            E4 -> "Mi4"
            F4 -> "Fa4"
            FS4 -> "Fa#4"
            G4 -> "Sol4"
            GS4 -> "Sol#4"
            A4 -> "La4"
            AS4 -> "La#4"
            B4 -> "Si4"
        }
}
