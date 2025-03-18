package br.com.fiap.financialeducationapp.ui.screens.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.financialeducationapp.data.model.BudgetEntry
import br.com.fiap.financialeducationapp.data.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository
) : ViewModel() {

    val budgetEntries: Flow<List<BudgetEntry>> = budgetRepository.getAllBudgetEntries()

    val totalIncome: Flow<Double> = budgetEntries.map { entries ->
        entries.filter { !it.isExpense }.sumOf { it.amount }
    }

    val totalExpenses: Flow<Double> = budgetEntries.map { entries ->
        entries.filter { it.isExpense }.sumOf { it.amount }
    }

    fun addBudgetEntry(category: String, amount: Double, isExpense: Boolean) {
        viewModelScope.launch {
            val entry = BudgetEntry(
                id = UUID.randomUUID().toString(),
                amount = amount,
                category = category,
                date = System.currentTimeMillis(),
                isExpense = isExpense
            )
            budgetRepository.addBudgetEntry(entry)
        }
    }

    fun deleteBudgetEntry(entry: BudgetEntry) {
        viewModelScope.launch {
            budgetRepository.deleteBudgetEntry(entry)
        }
    }
}