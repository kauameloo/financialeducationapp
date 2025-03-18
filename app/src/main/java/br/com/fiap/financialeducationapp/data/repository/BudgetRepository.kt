package br.com.fiap.financialeducationapp.data.repository

import br.com.fiap.financialeducationapp.data.local.dao.BudgetDao
import br.com.fiap.financialeducationapp.data.local.entity.BudgetEntryEntity
import br.com.fiap.financialeducationapp.data.model.BudgetEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BudgetRepository @Inject constructor(
    private val budgetDao: BudgetDao
) {
    fun getAllBudgetEntries(): Flow<List<BudgetEntry>> {
        return budgetDao.getAllBudgetEntries().map { entities ->
            entities.map { it.toBudgetEntry() }
        }
    }

    fun getBudgetEntriesForPeriod(startDate: Long, endDate: Long): Flow<List<BudgetEntry>> {
        return budgetDao.getBudgetEntriesForPeriod(startDate, endDate).map { entities ->
            entities.map { it.toBudgetEntry() }
        }
    }

    suspend fun addBudgetEntry(entry: BudgetEntry) {
        budgetDao.insertBudgetEntry(entry.toEntity())
    }

    suspend fun deleteBudgetEntry(entry: BudgetEntry) {
        budgetDao.deleteBudgetEntry(entry.toEntity())
    }

    private fun BudgetEntryEntity.toBudgetEntry(): BudgetEntry {
        return BudgetEntry(
            id = id,
            amount = amount,
            category = category,
            date = date,
            isExpense = isExpense
        )
    }

    private fun BudgetEntry.toEntity(): BudgetEntryEntity {
        return BudgetEntryEntity(
            id = id,
            amount = amount,
            category = category,
            date = date,
            isExpense = isExpense
        )
    }
}