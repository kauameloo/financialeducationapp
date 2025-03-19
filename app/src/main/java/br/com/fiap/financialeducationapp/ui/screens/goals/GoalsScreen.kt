package br.com.fiap.financialeducationapp.ui.screens.goals

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import br.com.fiap.financialeducationapp.data.model.SavingsGoal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun GoalsScreen(
    viewModel: GoalsViewModel = hiltViewModel()
) {
    val goals by viewModel.goals.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var selectedGoal by remember { mutableStateOf<SavingsGoal?>(null) }
    var showUpdateDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Metas de Economia",
                fontSize = 24.sp,
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
                    contentDescription = "Adicionar meta"
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(goals) { goal ->
                GoalItem(
                    goal = goal,
                    onUpdate = {
                        selectedGoal = goal
                        showUpdateDialog = true
                    },
                    onDelete = { viewModel.deleteGoal(goal) }
                )
            }
        }
    }

    if (showAddDialog) {
        AddGoalDialog(
            onDismiss = { showAddDialog = false },
            onAddGoal = { title, targetAmount, deadline ->
                viewModel.addGoal(title, targetAmount, deadline)
                showAddDialog = false
            }
        )
    }

    if (showUpdateDialog && selectedGoal != null) {
        UpdateGoalDialog(
            goal = selectedGoal!!,
            onDismiss = { showUpdateDialog = false },
            onUpdateGoal = { newAmount ->
                viewModel.updateGoalAmount(selectedGoal!!, newAmount)
                showUpdateDialog = false
            }
        )
    }
}

@Composable
fun GoalItem(
    goal: SavingsGoal,
    onUpdate: () -> Unit,
    onDelete: () -> Unit
) {
    val progress = (goal.currentAmount / goal.targetAmount).coerceIn(0.0, 1.0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = goal.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Row {
                    IconButton(
                        onClick = onUpdate,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Atualizar meta",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Excluir meta",
                            tint = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = progress.toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${formatCurrency(goal.currentAmount)} de ${formatCurrency(goal.targetAmount)}",
                    fontSize = 14.sp
                )

                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (goal.deadline != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Prazo: ${formatDate(goal.deadline)}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun AddGoalDialog(
    onDismiss: () -> Unit,
    onAddGoal: (String, Double, Long?) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var hasDeadline by remember { mutableStateOf(false) }
    var deadlineText by remember { mutableStateOf("") }

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
                    text = "Nova Meta",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Valor Alvo (R$)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Checkbox(
                        checked = hasDeadline,
                        onCheckedChange = { hasDeadline = it }
                    )
                    Text("Definir prazo")
                }

                if (hasDeadline) {
                    OutlinedTextField(
                        value = deadlineText,
                        onValueChange = { deadlineText = it },
                        label = { Text("Prazo (DD/MM/AAAA)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
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
                            val deadline = if (hasDeadline) parseDate(deadlineText) else null

                            if (title.isNotBlank() && amount > 0) {
                                onAddGoal(title, amount, deadline)
                            }
                        },
                        enabled = title.isNotBlank() && amountText.toDoubleOrNull() != null &&
                                (!hasDeadline || isValidDate(deadlineText))
                    ) {
                        Text("Adicionar")
                    }
                }
            }
        }
    }
}

@Composable
fun UpdateGoalDialog(
    goal: SavingsGoal,
    onDismiss: () -> Unit,
    onUpdateGoal: (Double) -> Unit
) {
    var currentAmountText by remember { mutableStateOf(goal.currentAmount.toString()) }

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
                    text = "Atualizar Meta: ${goal.title}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Valor Alvo: ${formatCurrency(goal.targetAmount)}",
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = currentAmountText,
                    onValueChange = { currentAmountText = it },
                    label = { Text("Valor Atual (R$)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

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
                            val newAmount = currentAmountText.toDoubleOrNull() ?: 0.0
                            onUpdateGoal(newAmount)
                        },
                        enabled = currentAmountText.toDoubleOrNull() != null
                    ) {
                        Text("Atualizar")
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

private fun parseDate(dateString: String): Long? {
    return try {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        dateFormat.parse(dateString)?.time
    } catch (e: Exception) {
        null
    }
}

private fun isValidDate(dateString: String): Boolean {
    return try {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        dateFormat.parse(dateString)
        true
    } catch (e: Exception) {
        false
    }
}