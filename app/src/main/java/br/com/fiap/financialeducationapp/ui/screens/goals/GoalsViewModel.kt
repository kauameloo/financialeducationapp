package br.com.fiap.financialeducationapp.ui.screens.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.financialeducationapp.data.model.SavingsGoal
import br.com.fiap.financialeducationapp.data.repository.GoalsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val goalsRepository: GoalsRepository
) : ViewModel() {

    val goals: StateFlow<List<SavingsGoal>> = goalsRepository.getAllSavingsGoals()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addGoal(title: String, targetAmount: Double, deadline: Long?) {
        viewModelScope.launch {
            goalsRepository.addSavingsGoal(
                title = title,
                targetAmount = targetAmount,
                deadline = deadline
            )
        }
    }

    fun updateGoalAmount(goal: SavingsGoal, newAmount: Double) {
        viewModelScope.launch {
            goalsRepository.updateSavingsGoalAmount(goal, newAmount)
        }
    }

    fun deleteGoal(goal: SavingsGoal) {
        viewModelScope.launch {
            goalsRepository.deleteSavingsGoal(goal)
        }
    }
}