package ru.kpfu.itis.hw_android_2022.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kpfu.itis.hw_android_2022.dao.UserDao
import ru.kpfu.itis.hw_android_2022.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}