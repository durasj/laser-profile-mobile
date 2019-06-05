package me.duras.laserprofile.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class, Game::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao
}
