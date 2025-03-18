package br.com.fiap.financialeducationapp.data.model

data class Course(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val modules: List<Module> = emptyList(),
    val imageUrl: String = "",
    val difficulty: String = "Beginner"
)