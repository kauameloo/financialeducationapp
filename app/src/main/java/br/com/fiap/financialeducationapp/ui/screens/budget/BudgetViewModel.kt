package br.com.fiap.financialeducationapp.ui.screens.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.financialeducationapp.data.model.BudgetEntry
import br.com.fiap.financialeducationapp.ui.components.SectionHeader
import br.com.fiap.financialeducationapp.ui.theme.ExpenseRed
import br.com.fiap.financialeducationapp.ui.theme.IncomeGreen
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(
    navController: NavController,
    viewModel: BudgetViewModel = viewModel()
) {
    val budgetEntries by viewModel.budgetEntries.collectAsState(initial = emptyList())
    val totalIncome by viewModel.totalIncome.collectAsState(initial = 0.0)
    val totalExpenses by viewModel.totalExpenses.collectAsState(initial = 0.0)
    val balance = totalIncome - totalExpenses

    var showAddDialog by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var isExpense by remember { mutableStateOf(false) }

    val currencyFormatter = remember { NumberFormat.getCurrencyInstance(Locale("pt", "BR")) }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Orçamento",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Gerencie suas receitas e despesas",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Resumo do orçamento
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Resumo do Orçamento",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Receitas: ${currencyFormatter.format(totalIncome)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = IncomeGreen
                )

                Text(
                    text = "Despesas: ${currencyFormatter.format(totalExpenses)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = ExpenseRed,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "Saldo: ${currencyFormatter.format(balance)}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = if (balance >= 0) IncomeGreen else ExpenseRed,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botão de adicionar
        Button(
            onClick = { showAddDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Adicionar",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Adicionar Transação")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de transações
        SectionHeader(title = "Transações Recentes")

        if (budgetEntries.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nenhuma transação registrada",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) {
                items(budgetEntries) { entry ->
                    BudgetEntryItem(
                        entry = entry,
                        currencyFormatter = currencyFormatter,
                        dateFormatter = dateFormatter,
                        onDelete = { viewModel.deleteBudgetEntry(entry) }
                    )
                }
            }
        }
    }

    // Diálogo para adicionar nova entrada
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Adicionar Transação") },
            text = {
                Column {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Categoria") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Valor") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = !isExpense,
                            onClick = { isExpense = false }
                        )
                        Text("Receita", modifier = Modifier.padding(start = 8.dp))

                        Spacer(modifier = Modifier.width(16.dp))

                        RadioButton(
                            selected = isExpense,
                            onClick = { isExpense = true }
                        )
                        Text("Despesa", modifier = Modifier.padding(start = 8.dp))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amountValue = amount.toDoubleOrNull() ?: 0.0
                        if (category.isNotBlank() && amountValue > 0) {
                            viewModel.addBudgetEntry(category, amountValue, isExpense)

                            category = ""
                            amount = ""
                            isExpense = false
                            showAddDialog = false
                        }
                    }
                ) {
                    Text("Adicionar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showAddDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun BudgetEntryItem(
    entry: BudgetEntry,
    currencyFormatter: NumberFormat,
    dateFormatter: SimpleDateFormat,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = entry.category,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = dateFormatter.format(Date(entry.date)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Text(
                    text = currencyFormatter.format(entry.amount),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (!entry.isExpense) IncomeGreen else ExpenseRed
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Excluir",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}