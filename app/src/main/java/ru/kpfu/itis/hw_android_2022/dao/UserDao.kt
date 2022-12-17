package ru.kpfu.itis.hw_android_2022.dao

import androidx.room.*
import ru.kpfu.itis.hw_android_2022.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: UserEntity)

    @Transaction
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getById(id: Int): UserEntity

    // Пример удаления всех данных из таблицы через Query
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Transaction
    @Query("SELECT * from users where username = :username and password = :password")
    suspend fun findUser(username: String, password: String): UserEntity?

    @Transaction
    suspend fun updateAllData() {
        deleteAllUsers()
    }
}