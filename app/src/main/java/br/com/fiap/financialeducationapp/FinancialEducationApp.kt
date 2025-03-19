package br.com.fiap.financialeducationapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import br.com.fiap.financialeducationapp.data.repository.BudgetRepository
import br.com.fiap.financialeducationapp.data.repository.CoursesRepository
import br.com.fiap.financialeducationapp.data.repository.GoalsRepository
import br.com.fiap.financialeducationapp.data.repository.UserRepository
import javax.inject.Inject

@HiltAndroidApp
class FinancialEducationApp : Application() {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var budgetRepository: BudgetRepository

    @Inject
    lateinit var goalsRepository: GoalsRepository

    @Inject
    lateinit var coursesRepository: CoursesRepository

    private val applicationScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        // Inicializar o banco de dados com dados de exemplo
        applicationScope.launch {
            // Criar um usuário padrão se não existir
            userRepository.createDefaultUserIfNotExists()

            // Adicionar dados de exemplo para as outras entidades
            budgetRepository.addSampleData()
            goalsRepository.addSampleData()
            coursesRepository.addSampleData()
        }
    }
}