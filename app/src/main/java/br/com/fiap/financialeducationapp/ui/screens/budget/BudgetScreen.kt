package br.com.fiap.financialeducationapp.ui.screens.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.fiap.financialeducationapp.data.model.BudgetEntry
import br.com.fiap.financialeducationapp.ui.theme.Green
import br.com.fiap.financialeducationapp.ui.theme.Red
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BudgetScreen(
    viewModel: BudgetViewModel = hiltViewModel()
) {
    val budgetEntries by viewModel.budgetEntries.collectAsState()
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpenses by viewModel.totalExpenses.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Orçamento",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Resumo financeiro
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Resumo Financeiro",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Receitas:")
                    Text(
                        text = formatCurrency(totalIncome),
                        color = Green
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Despesas:")
                    Text(
                        text = formatCurrency(totalExpenses),
                        color = Red
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Saldo:",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = formatCurrency(totalIncome - totalExpenses),
                        fontWeight = FontWeight.Bold,
                        color = if (totalIncome - totalExpenses >= 0) Green else Red
                    )
                }
            }
        }

        // Lista de transações
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Transações Recentes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar transação"
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(budgetEntries) { entry ->
                BudgetEntryItem(
                    entry = entry,
                    onDelete = { viewModel.deleteBudgetEntry(entry) }
                )
            }
        }
    }

    if (showAddDialog) {
        AddBudgetEntryDialog(
            onDismiss = { showAddDialog = false },
            onAddEntry = { category, amount, isExpense ->
                viewModel.addBudgetEntry(category, amount, isExpense)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun BudgetEntryItem(
    entry: BudgetEntry,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = entry.category,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = formatDate(entry.date),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Text(
                text = formatCurrency(entry.amount),
                fontWeight = FontWeight.Bold,
                color = if (entry.isExpense) Red else Green
            )

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Excluir",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun AddBudgetEntryDialog(
    onDismiss: () -> Unit,
    onAddEntry: (String, Double, Boolean) -> Unit
) {
    var category by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var isExpense by remember { mutableStateOf(true) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Nova Transação",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Categoria") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Valor") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isExpense,
                            onClick = { isExpense = true }
                        )
                        Text("Despesa")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = !isExpense,
                            onClick = { isExpense = false }
                        )
                        Text("Receita")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            val amount = amountText.toDoubleOrNull() ?: 0.0
                            if (category.isNotBlank() && amount > 0) {
                                onAddEntry(category, amount, isExpense)
                            }
                        },
                        enabled = category.isNotBlank() && amountText.toDoubleOrNull() != null
                    ) {
                        Text("Adicionar")
                    }
                }
            }
        }
    }
}

// Funções utilitárias
private fun formatCurrency(value: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return format.format(value)
}

private fun formatDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    return dateFormat.format(Date(timestamp))
}