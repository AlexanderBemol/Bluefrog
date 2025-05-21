package com.amontdevs.bluefrog.domain.absolute

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CustomSession(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val isPredefined: Boolean = false,
    val title: String = "",
    val description: String = "",
    val notes: List<SessionAbsoluteNote> = emptyList(),
)
