package br.com.fiap.financialeducationapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val incomeRange: String,
    val financialGoals: List<String>
)