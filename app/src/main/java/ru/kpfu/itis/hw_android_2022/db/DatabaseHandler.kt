package ru.kpfu.itis.hw_android_2022.db

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kpfu.itis.hw_android_2022.dao.UserDao
import ru.kpfu.itis.hw_android_2022.mappers.UserMapper
import ru.kpfu.itis.hw_android_2022.models.UserModel

object DatabaseHandler {
    private var userDb: UserDao? = null

    fun initializeDb(applicationContext: Context) {
        if (userDb == null) {
            val db = Room.databaseBuilder(
                applicationContext,
                UserDatabase::class.java, "USER_DATABASE"
            ).build()
            userDb = db.userDao()
        }
    }

    suspend fun createUser(userModel: UserModel) =
        withContext(Dispatchers.IO) {
            userDb?.createUser(UserMapper.map(userModel))
        }

    suspend fun getUsers(): List<UserModel>? =
        withContext(Dispatchers.IO) {
            return@withContext userDb?.getAllUsers()?.map {
                UserMapper.map(it)
            }
        }

    suspend fun getUserById(id: Int) = withContext(Dispatchers.IO) {
        return@withContext userDb?.getById(id)
    }

    suspend fun findUser(username: String, password: String) = withContext(Dispatchers.IO) {
        return@withContext userDb?.findUser(username, password)
    }

    suspend fun deleteAllUsers() {
        withContext(Dispatchers.IO) {
            userDb?.deleteAllUsers()
        }
    }
}