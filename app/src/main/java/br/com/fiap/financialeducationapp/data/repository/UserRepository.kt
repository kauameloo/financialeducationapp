package br.com.fiap.financialeducationapp.data.repository

import br.com.fiap.financialeducationapp.data.dao.UserDao
import br.com.fiap.financialeducationapp.data.model.User
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userDao: UserDao) {

    fun getUser(): Flow<User?> = userDao.getUser()

    suspend fun createOrUpdateUser(
        name: String,
        email: String,
        incomeRange: String,
        financialGoals: List<String>
    ) {
        val existingUser = userDao.getUser()

        // Verificar se já existe um usuário e atualizá-lo, ou criar um novo
        val user = User(
            id = UUID.randomUUID().toString(),
            name = name,
            email = email,
            incomeRange = incomeRange,
            financialGoals = financialGoals
        )

        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    // Método para criar um usuário padrão se não existir nenhum
    suspend fun createDefaultUserIfNotExists() {
        val defaultUser = User(
            id = UUID.randomUUID().toString(),
            name = "João Silva",
            email = "joao.silva@exemplo.com",
            incomeRange = "R$ 3.000 - R$ 5.000",
            financialGoals = listOf("Economizar para aposentadoria", "Comprar uma casa", "Investir em educação")
        )

        userDao.insertUser(defaultUser)
    }
}