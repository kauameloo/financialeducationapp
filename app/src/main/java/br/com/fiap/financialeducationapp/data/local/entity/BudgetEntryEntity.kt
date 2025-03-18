package br.com.fiap.financialeducationapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_entries")
data class BudgetEntryEntity(
    @PrimaryKey val id: String,
    val amount: Double,
    val category: String,
    val date: Long,
    val isExpense: Boolean
)