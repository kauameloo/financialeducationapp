package br.com.fiap.financialeducationapp.data.repository

import br.com.fiap.financialeducationapp.data.local.dao.SavingsGoalDao
import br.com.fiap.financialeducationapp.data.local.entity.SavingsGoalEntity
import br.com.fiap.financialeducationapp.data.model.SavingsGoal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SavingsGoalRepository @Inject constructor(
    private val savingsGoalDao: SavingsGoalDao
) {
    fun getAllSavingsGoals(): Flow<List<SavingsGoal>> {
        return savingsGoalDao.getAllSavingsGoals().map { entities ->
            entities.map { it.toSavingsGoal() }
        }
    }

    suspend fun addSavingsGoal(goal: SavingsGoal) {
        savingsGoalDao.insertSavingsGoal(goal.toEntity())
    }

    suspend fun updateSavingsGoal(goal: SavingsGoal) {
        savingsGoalDao.updateSavingsGoal(goal.toEntity())
    }

    suspend fun deleteSavingsGoal(goal: SavingsGoal) {
        savingsGoalDao.deleteSavingsGoal(goal.toEntity())
    }

    private fun SavingsGoalEntity.toSavingsGoal(): SavingsGoal {
        return SavingsGoal(
            id = id,
            title = title,
            targetAmount = targetAmount,
            currentAmount = currentAmount,
            deadline = deadline
        )
    }

    private fun SavingsGoal.toEntity(): SavingsGoalEntity {
        return SavingsGoalEntity(
            id = id,
            title = title,
            targetAmount = targetAmount,
            currentAmount = currentAmount,
            deadline = deadline
        )
    }
}