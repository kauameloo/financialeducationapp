package br.com.fiap.financialeducationapp.ui.screens.goals

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.financialeducationapp.data.model.SavingsGoal
import br.com.fiap.financialeducationapp.ui.components.SectionHeader
import br.com.fiap.financialeducationapp.ui.theme.ProgressBlue
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(
    navController: NavController,
    viewModel: GoalsViewModel = viewModel()
) {
    val goals by viewModel.goals.collectAsState(initial = emptyList())

    var showAddDialog by remember { mutableStateOf(false) }
    var showDepositDialog by remember { mutableStateOf(false) }
    var selectedGoal by remember { mutableStateOf<SavingsGoal?>(null) }

    var title by remember { mutableStateOf("") }
    var targetAmount by remember { mutableStateOf("") }
    var targetDate by remember { mutableStateOf("") }
    var depositAmount by remember { mutableStateOf("") }

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
            text = "Metas de Economia",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Defina objetivos e acompanhe seu progresso",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Card informativo
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Dica para Economizar",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Defina metas específicas e realistas. Divida grandes objetivos em etapas menores para facilitar o acompanhamento.",
                    style = MaterialTheme.typography.bodyMedium
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
            Text("Criar Nova Meta")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de metas
        SectionHeader(title = "Suas Metas")

        if (goals.isEmpty()) {
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
                        text = "Você ainda não tem metas definidas",
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
                items(goals) { goal ->
                    GoalItem(
                        goal = goal,
                        currencyFormatter = currencyFormatter,
                        dateFormatter = dateFormatter,
                        onDelete = { viewModel.deleteGoal(goal) },
                        onDeposit = {
                            selectedGoal = goal
                            showDepositDialog = true
                        }
                    )
                }
            }
        }
    }

    // Diálogo para adicionar nova meta
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Criar Nova Meta") },
            text = {
                Column {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Título da Meta") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    OutlinedTextField(
                        value = targetAmount,
                        onValueChange = { targetAmount = it },
                        label = { Text("Valor Alvo (R$)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    OutlinedTextField(
                        value = targetDate,
                        onValueChange = { targetDate = it },
                        label = { Text("Data Alvo (DD/MM/AAAA)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        try {
                            val amount = targetAmount.toDoubleOrNull() ?: 0.0
                            val deadline = try {
                                dateFormatter.parse(targetDate)?.time
                            } catch (e: Exception) {
                                null
                            }

                            if (title.isNotBlank() && amount > 0) {
                                viewModel.addGoal(title, amount, deadline)

                                title = ""
                                targetAmount = ""
                                targetDate = ""
                                showAddDialog = false
                            }
                        } catch (e: Exception) {
                            // Tratar erro de formato de data
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

    // Diálogo para adicionar depósito
    if (showDepositDialog && selectedGoal != null) {
        AlertDialog(
            onDismissRequest = {
                showDepositDialog = false
                selectedGoal = null
            },
            title = { Text("Adicionar Depósito") },
            text = {
                Column {
                    Text(
                        text = "Meta: ${selectedGoal?.title}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = depositAmount,
                        onValueChange = { depositAmount = it },
                        label = { Text("Valor do Depósito (R$)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amount = depositAmount.toDoubleOrNull() ?: 0.0
                        selectedGoal?.let { goal ->
                            viewModel.updateGoalAmount(goal, goal.currentAmount + amount)
                        }

                        depositAmount = ""
                        showDepositDialog = false
                        selectedGoal = null
                    }
                ) {
                    Text("Depositar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDepositDialog = false
                        selectedGoal = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun GoalItem(
    goal: SavingsGoal,
    currencyFormatter: NumberFormat,
    dateFormatter: SimpleDateFormat,
    onDelete: () -> Unit,
    onDeposit: () -> Unit
) {
    val progress = (goal.currentAmount / goal.targetAmount).coerceIn(0.0, 1.0).toFloat()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Row {
                    IconButton(onClick = onDeposit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Depositar",
                            tint = MaterialTheme.colorScheme.primary
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Meta: ${currencyFormatter.format(goal.targetAmount)}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Atual: ${currencyFormatter.format(goal.currentAmount)}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            goal.deadline?.let { deadline ->
                Text(
                    text = "Data alvo: ${dateFormatter.format(Date(deadline))}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = ProgressBlue,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${(progress * 100).toInt()}% concluído",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}