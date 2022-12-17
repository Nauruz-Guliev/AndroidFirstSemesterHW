package ru.kpfu.itis.hw_android_2022.mappers

import ru.kpfu.itis.hw_android_2022.entities.UserEntity
import ru.kpfu.itis.hw_android_2022.models.UserModel

object UserMapper {
    fun map(user: UserModel): UserEntity {
        with(user) {
            return UserEntity(
                id = id,
                userName = userName,
                userPassword = userPassword
            )
        }
    }

    fun map(user: UserEntity): UserModel {
        with(user) {
            return UserModel(
                id = id,
                userName = userName,
                userPassword = userPassword
            )
        }
    }

}