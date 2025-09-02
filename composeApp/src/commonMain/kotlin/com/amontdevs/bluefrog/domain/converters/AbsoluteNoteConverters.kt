package com.amontdevs.bluefrog.domain.converters

import androidx.room.TypeConverter
import com.amontdevs.bluefrog.domain.absolute.AbsoluteNote

class AbsoluteNoteConverters {
    @TypeConverter
    fun fromAbsoluteNote(absoluteNote: AbsoluteNote): Int = absoluteNote.code

    @TypeConverter
    fun toAbsoluteNote(code: Int): AbsoluteNote = AbsoluteNote.getFromCode(code)

    @TypeConverter
    fun fromAbsoluteNoteList(absoluteNotes: List<AbsoluteNote>): String {
        return ""
    }

    @TypeConverter
    fun toAbsoluteNoteList(string: String): List<AbsoluteNote> {
        return emptyList()
    }
}

