package br.com.fiap.financialeducationapp.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val incomeRange: String = "",
    val financialGoals: List<String> = emptyList()
)