package br.com.fiap.financialeducationapp.data.repository

import br.com.fiap.financialeducationapp.data.model.Course
import br.com.fiap.financialeducationapp.data.model.Module
import javax.inject.Inject

class CourseRepository @Inject constructor() {
    // In a real app, this would fetch from an API
    // For this example, we'll use hardcoded data
    fun getCourses(): List<Course> {
        return listOf(
            Course(
                id = "1",
                title = "Fundamentos de Finanças Pessoais",
                description = "Aprenda os conceitos básicos para gerenciar seu dinheiro de forma eficiente.",
                imageUrl = "https://example.com/finance101.jpg",
                modules = listOf(
                    Module(
                        id = "1-1",
                        title = "Orçamento Básico",
                        content = "Um orçamento é um plano para o seu dinheiro. Neste módulo, você aprenderá como criar um orçamento simples que funcione para você."
                    ),
                    Module(
                        id = "1-2",
                        title = "Economizando Dinheiro",
                        content = "Aprenda estratégias práticas para economizar dinheiro, mesmo com um orçamento apertado."
                    )
                )
            ),
            Course(
                id = "2",
                title = "Saindo das Dívidas",
                description = "Estratégias práticas para sair das dívidas e manter-se livre delas.",
                imageUrl = "https://example.com/debtfree.jpg",
                modules = listOf(
                    Module(
                        id = "2-1",
                        title = "Entendendo suas Dívidas",
                        content = "O primeiro passo para sair das dívidas é entender completamente sua situação atual."
                    ),
                    Module(
                        id = "2-2",
                        title = "Métodos de Pagamento de Dívidas",
                        content = "Conheça diferentes estratégias para pagar suas dívidas de forma eficiente."
                    )
                )
            ),
            Course(
                id = "3",
                title = "Primeiros Passos para Investir",
                description = "Aprenda como começar a investir com pouco dinheiro.",
                imageUrl = "https://example.com/investing.jpg",
                modules = listOf(
                    Module(
                        id = "3-1",
                        title = "Por que Investir?",
                        content = "Entenda por que investir é importante para seu futuro financeiro."
                    ),
                    Module(
                        id = "3-2",
                        title = "Opções de Investimento para Iniciantes",
                        content = "Conheça opções de investimento acessíveis para quem está começando."
                    )
                )
            )
        )
    }

    fun getCourseById(courseId: String): Course? {
        return getCourses().find { it.id == courseId }
    }
}