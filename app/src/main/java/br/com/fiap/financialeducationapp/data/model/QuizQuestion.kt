package br.com.fiap.financialeducationapp.data.model

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)