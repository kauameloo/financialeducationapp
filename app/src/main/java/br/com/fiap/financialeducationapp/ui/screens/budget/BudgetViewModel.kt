package br.com.fiap.financialeducationapp.ui.screens.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.financialeducationapp.data.model.BudgetEntry
import br.com.fiap.financialeducationapp.data.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository
) : ViewModel() {

    val budgetEntries: StateFlow<List<BudgetEntry>> = budgetRepository.getAllBudgetEntries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalIncome: StateFlow<Double> = budgetRepository.getAllIncome()
        .map { entries -> entries.sumOf { it.amount } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    val totalExpenses: StateFlow<Double> = budgetRepository.getAllExpenses()
        .map { entries -> entries.sumOf { it.amount } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    fun addBudgetEntry(category: String, amount: Double, isExpense: Boolean) {
        viewModelScope.launch {
            budgetRepository.addBudgetEntry(
                amount = amount,
                category = category,
                date = System.currentTimeMillis(),
                isExpense = isExpense
            )
        }
    }

    fun deleteBudgetEntry(entry: BudgetEntry) {
        viewModelScope.launch {
            budgetRepository.deleteBudgetEntry(entry)
        }
    }
}