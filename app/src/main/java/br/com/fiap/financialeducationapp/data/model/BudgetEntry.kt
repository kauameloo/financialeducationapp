package br.com.fiap.financialeducationapp.data.model

data class BudgetEntry(
    val id: String = "",
    val amount: Double,
    val category: String,
    val date: Long,
    val isExpense: Boolean
)