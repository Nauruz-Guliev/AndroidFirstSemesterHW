package ru.kpfu.itis.hw_android_2022.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE 'users' " +
                        "('id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,'username' TEXT NOT NULL,'password' TEXT NOT NULL)"
            )
        }
    }
}