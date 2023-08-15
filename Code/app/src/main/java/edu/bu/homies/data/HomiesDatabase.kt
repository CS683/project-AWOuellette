package edu.bu.homies.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Home::class],
    version = 1
)

@TypeConverters(Converters:: class)
abstract class HomiesDatabase: RoomDatabase() {
    abstract fun homeDao(): HomeDao
}