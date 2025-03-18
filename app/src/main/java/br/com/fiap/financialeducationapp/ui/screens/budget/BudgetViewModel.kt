package br.com.fiap.financialeducationapp.ui.screens.budget

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.financialeducationapp.data.model.BudgetEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID

class BudgetViewModel(application: Application) : AndroidViewModel(application) {

    private val _budgetEntries = MutableStateFlow<List<BudgetEntry>>(emptyList())
    val budgetEntries: StateFlow<List<BudgetEntry>> = _budgetEntries

    val totalIncome = budgetEntries.map { entries ->
        entries.filter { !it.isExpense }.sumOf { it.amount }
    }

    val totalExpenses = budgetEntries.map { entries ->
        entries.filter { it.isExpense }.sumOf { it.amount }
    }

    // Dados de exemplo para teste
    init {
        // Adicionar alguns dados de exemplo
        val sampleEntries = listOf(
            BudgetEntry(
                id = UUID.randomUUID().toString(),
                amount = 2500.0,
                category = "Salário",
                date = System.currentTimeMillis(),
                isExpense = false
            ),
            BudgetEntry(
                id = UUID.randomUUID().toString(),
                amount = 800.0,
                category = "Aluguel",
                date = System.currentTimeMillis(),
                isExpense = true
            ),
            BudgetEntry(
                id = UUID.randomUUID().toString(),
                amount = 350.0,
                category = "Supermercado",
                date = System.currentTimeMillis(),
                isExpense = true
            ),
            BudgetEntry(
                id = UUID.randomUUID().toString(),
                amount = 200.0,
                category = "Transporte",
                date = System.currentTimeMillis(),
                isExpense = true
            )
        )
        _budgetEntries.value = sampleEntries
    }

    fun addBudgetEntry(category: String, amount: Double, isExpense: Boolean) {
        val entry = BudgetEntry(
            id = UUID.randomUUID().toString(),
            amount = amount,
            category = category,
            date = System.currentTimeMillis(),
            isExpense = isExpense
        )
        _budgetEntries.value = _budgetEntries.value + entry
    }

    fun deleteBudgetEntry(entry: BudgetEntry) {
        _budgetEntries.value = _budgetEntries.value.filter { it.id != entry.id }
    }
}