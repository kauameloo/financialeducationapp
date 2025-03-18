package br.com.fiap.financialeducationapp.data.repository

import br.com.fiap.financialeducationapp.data.local.dao.UserDao
import br.com.fiap.financialeducationapp.data.local.entity.UserEntity
import br.com.fiap.financialeducationapp.data.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun getUser(): User? {
        return userDao.getUser()?.toUser()
    }

    suspend fun saveUser(user: User) {
        userDao.insertUser(user.toEntity())
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user.toEntity())
    }

    private fun UserEntity.toUser(): User {
        return User(
            id = id,
            name = name,
            email = email,
            incomeRange = incomeRange
        )
    }

    private fun User.toEntity(): UserEntity {
        return UserEntity(
            id = id,
            name = name,
            email = email,
            incomeRange = incomeRange
        )
    }
}