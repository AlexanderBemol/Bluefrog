package com.amontdevs.bluefrog.domain

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.amontdevs.bluefrog.domain.absolute.CustomSession
import com.amontdevs.bluefrog.domain.absolute.SessionAbsoluteNote

@Database(
    entities = [
        SessionAbsoluteNote::class,
        CustomSession::class,
    ],
    version = 1,
)
@ConstructedBy(AppDBConstructor::class)
abstract class AppDB : RoomDatabase()

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDBConstructor : RoomDatabaseConstructor<AppDB> {
    override fun initialize(): AppDB
}
