package br.com.fiap.financialeducationapp.data.model

data class SavingsGoal(
    val id: String = "",
    val title: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val deadline: Long? = null
)