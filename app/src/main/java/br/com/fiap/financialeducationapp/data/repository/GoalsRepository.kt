package br.com.fiap.financialeducationapp.data.repository

import br.com.fiap.financialeducationapp.data.dao.SavingsGoalDao
import br.com.fiap.financialeducationapp.data.model.SavingsGoal
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalsRepository @Inject constructor(private val savingsGoalDao: SavingsGoalDao) {

    fun getAllSavingsGoals(): Flow<List<SavingsGoal>> = savingsGoalDao.getAllSavingsGoals()

    suspend fun addSavingsGoal(title: String, targetAmount: Double, currentAmount: Double = 0.0, deadline: Long?) {
        val goal = SavingsGoal(
            id = UUID.randomUUID().toString(),
            title = title,
            targetAmount = targetAmount,
            currentAmount = currentAmount,
            deadline = deadline
        )
        savingsGoalDao.insertSavingsGoal(goal)
    }

    suspend fun updateSavingsGoalAmount(goal: SavingsGoal, newAmount: Double) {
        val updatedGoal = goal.copy(currentAmount = newAmount)
        savingsGoalDao.updateSavingsGoal(updatedGoal)
    }

    suspend fun deleteSavingsGoal(goal: SavingsGoal) {
        savingsGoalDao.deleteSavingsGoal(goal)
    }

    // Método para adicionar dados de exemplo
    suspend fun addSampleData() {
        val sampleGoals = listOf(
            SavingsGoal(
                id = UUID.randomUUID().toString(),
                title = "Fundo de Emergência",
                targetAmount = 10000.0,
                currentAmount = 3500.0,
                deadline = System.currentTimeMillis() + 15552000000 // 6 meses em milissegundos
            ),
            SavingsGoal(
                id = UUID.randomUUID().toString(),
                title = "Viagem de Férias",
                targetAmount = 5000.0,
                currentAmount = 1200.0,
                deadline = System.currentTimeMillis() + 31104000000 // 12 meses em milissegundos
            ),
            SavingsGoal(
                id = UUID.randomUUID().toString(),
                title = "Novo Notebook",
                targetAmount = 3000.0,
                currentAmount = 800.0,
                deadline = System.currentTimeMillis() + 7776000000 // 3 meses em milissegundos
            )
        )

        sampleGoals.forEach { savingsGoalDao.insertSavingsGoal(it) }
    }
}