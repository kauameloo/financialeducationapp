package br.com.fiap.financialeducationapp.ui.screens.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.financialeducationapp.data.model.SavingsGoal
import br.com.fiap.financialeducationapp.data.repository.SavingsGoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val savingsGoalRepository: SavingsGoalRepository
) : ViewModel() {

    val goals: Flow<List<SavingsGoal>> = savingsGoalRepository.getAllSavingsGoals()

    fun addGoal(title: String, targetAmount: Double, deadline: Long?) {
        viewModelScope.launch {
            val goal = SavingsGoal(
                id = UUID.randomUUID().toString(),
                title = title,
                targetAmount = targetAmount,
                currentAmount = 0.0,
                deadline = deadline
            )
            savingsGoalRepository.addSavingsGoal(goal)
        }
    }

    fun updateGoalAmount(goal: SavingsGoal, newAmount: Double) {
        viewModelScope.launch {
            val updatedGoal = goal.copy(currentAmount = newAmount)
            savingsGoalRepository.updateSavingsGoal(updatedGoal)
        }
    }

    fun deleteGoal(goal: SavingsGoal) {
        viewModelScope.launch {
            savingsGoalRepository.deleteSavingsGoal(goal)
        }
    }
}