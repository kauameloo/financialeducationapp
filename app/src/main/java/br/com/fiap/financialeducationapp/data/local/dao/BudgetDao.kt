package br.com.fiap.financialeducationapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.fiap.financialeducationapp.data.local.entity.BudgetEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budget_entries ORDER BY date DESC")
    fun getAllBudgetEntries(): Flow<List<BudgetEntryEntity>>

    @Query("SELECT * FROM budget_entries WHERE date BETWEEN :startDate AND :endDate")
    fun getBudgetEntriesForPeriod(startDate: Long, endDate: Long): Flow<List<BudgetEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudgetEntry(entry: BudgetEntryEntity)

    @Delete
    suspend fun deleteBudgetEntry(entry: BudgetEntryEntity)
}