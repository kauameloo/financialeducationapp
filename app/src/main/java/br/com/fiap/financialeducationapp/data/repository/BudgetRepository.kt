package br.com.fiap.financialeducationapp.data.repository

import br.com.fiap.financialeducationapp.data.dao.BudgetEntryDao
import br.com.fiap.financialeducationapp.data.model.BudgetEntry
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetRepository @Inject constructor(private val budgetEntryDao: BudgetEntryDao) {

    fun getAllBudgetEntries(): Flow<List<BudgetEntry>> = budgetEntryDao.getAllBudgetEntries()

    fun getAllIncome(): Flow<List<BudgetEntry>> = budgetEntryDao.getAllIncome()

    fun getAllExpenses(): Flow<List<BudgetEntry>> = budgetEntryDao.getAllExpenses()

    suspend fun addBudgetEntry(amount: Double, category: String, date: Long, isExpense: Boolean) {
        val entry = BudgetEntry(
            id = UUID.randomUUID().toString(),
            amount = amount,
            category = category,
            date = date,
            isExpense = isExpense
        )
        budgetEntryDao.insertBudgetEntry(entry)
    }

    suspend fun deleteBudgetEntry(entry: BudgetEntry) {
        budgetEntryDao.deleteBudgetEntry(entry)
    }

    // Método para adicionar dados de exemplo
    suspend fun addSampleData() {
        val sampleEntries = listOf(
            BudgetEntry(
                id = UUID.randomUUID().toString(),
                amount = 2500.0,
                category = "Salário",
                date = System.currentTimeMillis(),
                isExpense = false
            ),
            BudgetEntry(
                id = UUID.randomUUID().toString(),
                amount = 800.0,
                category = "Aluguel",
                date = System.currentTimeMillis(),
                isExpense = true
            ),
            BudgetEntry(
                id = UUID.randomUUID().toString(),
                amount = 350.0,
                category = "Supermercado",
                date = System.currentTimeMillis(),
                isExpense = true
            ),
            BudgetEntry(
                id = UUID.randomUUID().toString(),
                amount = 200.0,
                category = "Transporte",
                date = System.currentTimeMillis(),
                isExpense = true
            )
        )

        sampleEntries.forEach { budgetEntryDao.insertBudgetEntry(it) }
    }
}