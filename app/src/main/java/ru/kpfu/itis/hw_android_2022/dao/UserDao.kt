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

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Transaction
    @Query("SELECT * from users where username = :username and password = :password")
    suspend fun findUser(username: String, password: String): UserEntity?

    @Query("UPDATE users set username =:username where id=:id")
    suspend fun updateUser(username: String, id: Int)

    // а вдруг таких несколько, что невозможно, конечно, так как поле уникально
    @Query("SELECT id from users where username=:username limit 1")
    suspend fun findUserId(username: String): Int?


    @Transaction
    suspend fun updateAllData() {
        deleteAllUsers()
    }
}