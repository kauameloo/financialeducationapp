package br.com.fiap.financialeducationapp.data.repository

import br.com.fiap.financialeducationapp.data.dao.CourseDao
import br.com.fiap.financialeducationapp.data.model.Course
import br.com.fiap.financialeducationapp.data.model.CourseProgress
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoursesRepository @Inject constructor(private val courseDao: CourseDao) {

    fun getAllCourses(): Flow<List<Course>> = courseDao.getAllCourses()

    fun getCourseById(courseId: String): Flow<Course?> = courseDao.getCourseById(courseId)

    fun getCourseProgressForUser(userId: String, courseId: String): Flow<CourseProgress?> =
        courseDao.getCourseProgressForUser(userId, courseId)

    fun getAllCourseProgressForUser(userId: String): Flow<List<CourseProgress>> =
        courseDao.getAllCourseProgressForUser(userId)

    suspend fun addCourse(
        title: String,
        description: String,
        imageUrl: String,
        durationMinutes: Int,
        level: String,
        modules: List<String>
    ) {
        val course = Course(
            id = UUID.randomUUID().toString(),
            title = title,
            description = description,
            imageUrl = imageUrl,
            durationMinutes = durationMinutes,
            level = level,
            modules = modules
        )
        courseDao.insertCourse(course)
    }

    suspend fun updateCourseProgress(
        userId: String,
        courseId: String,
        completedModules: List<String>,
        isCompleted: Boolean
    ) {
        val courseProgress = CourseProgress(
            userId = userId,
            courseId = courseId,
            completedModules = completedModules,
            lastAccessTimestamp = System.currentTimeMillis(),
            isCompleted = isCompleted
        )
        courseDao.updateCourseProgress(courseProgress)
    }

    // Método para adicionar dados de exemplo
    suspend fun addSampleData() {
        val sampleCourses = listOf(
            Course(
                id = UUID.randomUUID().toString(),
                title = "Fundamentos de Finanças Pessoais",
                description = "Aprenda os conceitos básicos para organizar suas finanças e começar a poupar.",
                imageUrl = "https://example.com/finance101.jpg",
                durationMinutes = 60,
                level = "Iniciante",
                modules = listOf(
                    "Introdução às Finanças Pessoais",
                    "Criando um Orçamento",
                    "Controlando Gastos",
                    "Começando a Poupar"
                )
            ),
            Course(
                id = UUID.randomUUID().toString(),
                title = "Investimentos para Iniciantes",
                description = "Conheça os principais tipos de investimentos e como começar a investir com pouco dinheiro.",
                imageUrl = "https://example.com/investments101.jpg",
                durationMinutes = 90,
                level = "Intermediário",
                modules = listOf(
                    "Tipos de Investimentos",
                    "Renda Fixa vs. Renda Variável",
                    "Perfil de Investidor",
                    "Montando sua Primeira Carteira"
                )
            ),
            Course(
                id = UUID.randomUUID().toString(),
                title = "Saindo das Dívidas",
                description = "Estratégias práticas para sair das dívidas e recuperar sua saúde financeira.",
                imageUrl = "https://example.com/debtfree.jpg",
                durationMinutes = 75,
                level = "Iniciante",
                modules = listOf(
                    "Mapeando suas Dívidas",
                    "Métodos de Pagamento de Dívidas",
                    "Negociação com Credores",
                    "Mantendo-se Livre de Dívidas"
                )
            )
        )

        sampleCourses.forEach { courseDao.insertCourse(it) }
    }
}