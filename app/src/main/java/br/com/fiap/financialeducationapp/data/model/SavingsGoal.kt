package br.com.fiap.financialeducationapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savings_goals")
data class SavingsGoal(
    @PrimaryKey
    val id: String,
    val title: String,
    val targetAmount: Double,
    val currentAmount: Double,
    val deadline: Long?
)