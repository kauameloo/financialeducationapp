package br.com.fiap.financialeducationapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.fiap.financialeducationapp.data.model.BudgetEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetEntryDao {
    @Query("SELECT * FROM budget_entries ORDER BY date DESC")
    fun getAllBudgetEntries(): Flow<List<BudgetEntry>>

    @Query("SELECT * FROM budget_entries WHERE isExpense = 0 ORDER BY date DESC")
    fun getAllIncome(): Flow<List<BudgetEntry>>

    @Query("SELECT * FROM budget_entries WHERE isExpense = 1 ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<BudgetEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudgetEntry(budgetEntry: BudgetEntry)

    @Delete
    suspend fun deleteBudgetEntry(budgetEntry: BudgetEntry)
}