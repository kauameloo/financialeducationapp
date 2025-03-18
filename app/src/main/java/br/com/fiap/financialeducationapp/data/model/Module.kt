package br.com.fiap.financialeducationapp.data.model

data class Module(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val quizQuestions: List<QuizQuestion> = emptyList()
)