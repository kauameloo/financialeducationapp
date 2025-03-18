package br.com.fiap.financialeducationapp.ui.screens.goals

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.financialeducationapp.data.model.SavingsGoal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class GoalsViewModel(application: Application) : AndroidViewModel(application) {

    private val _goals = MutableStateFlow<List<SavingsGoal>>(emptyList())
    val goals: StateFlow<List<SavingsGoal>> = _goals

    // Dados de exemplo para teste
    init {
        // Adicionar alguns dados de exemplo
        val sampleGoals = listOf(
            SavingsGoal(
                id = UUID.randomUUID().toString(),
                title = "Fundo de Emergência",
                targetAmount = 10000.0,
                currentAmount = 3500.0,
                deadline = System.currentTimeMillis() + 15552000000 // 6 meses em milissegundos
            ),
            SavingsGoal(
                id = UUID.randomUUID().toString(),
                title = "Viagem de Férias",
                targetAmount = 5000.0,
                currentAmount = 1200.0,
                deadline = System.currentTimeMillis() + 31104000000 // 12 meses em milissegundos
            ),
            SavingsGoal(
                id = UUID.randomUUID().toString(),
                title = "Novo Notebook",
                targetAmount = 3000.0,
                currentAmount = 800.0,
                deadline = System.currentTimeMillis() + 7776000000 // 3 meses em milissegundos
            )
        )
        _goals.value = sampleGoals
    }

    fun addGoal(title: String, targetAmount: Double, deadline: Long?) {
        val goal = SavingsGoal(
            id = UUID.randomUUID().toString(),
            title = title,
            targetAmount = targetAmount,
            currentAmount = 0.0,
            deadline = deadline
        )
        _goals.value = _goals.value + goal
    }

    fun updateGoalAmount(goal: SavingsGoal, newAmount: Double) {
        val updatedGoal = goal.copy(currentAmount = newAmount)
        _goals.value = _goals.value.map { if (it.id == goal.id) updatedGoal else it }
    }

    fun deleteGoal(goal: SavingsGoal) {
        _goals.value = _goals.value.filter { it.id != goal.id }
    }
}