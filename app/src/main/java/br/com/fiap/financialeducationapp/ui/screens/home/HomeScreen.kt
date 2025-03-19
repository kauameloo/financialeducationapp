package br.com.fiap.financialeducationapp.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.fiap.financialeducationapp.ui.navigation.Screen
import br.com.fiap.financialeducationapp.ui.screens.budget.BudgetViewModel
import java.text.NumberFormat
import java.util.*

@Composable
fun HomeScreen(
    navController: NavController,
    budgetViewModel: BudgetViewModel = hiltViewModel()
) {
    val totalIncome by budgetViewModel.totalIncome.collectAsState()
    val totalExpenses by budgetViewModel.totalExpenses.collectAsState()
    val balance = totalIncome - totalExpenses

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Welcome section
        Text(
            text = "Bem-vindo ao FinEdu",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Seu caminho para a educação financeira",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Financial summary card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Resumo Financeiro",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Receitas: ${formatCurrency(totalIncome)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Despesas: ${formatCurrency(totalExpenses)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "Saldo: ${formatCurrency(balance)}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = if (balance >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Quick actions section
        SectionHeader(title = "Ações Rápidas")

        QuickActionButtons(
            onBudgetClick = { navController.navigate(Screen.Budget.route) },
            onGoalsClick = { navController.navigate(Screen.Goals.route) },
            onCoursesClick = { navController.navigate(Screen.Courses.route) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Financial tip of the day
        SectionHeader(title = "Dica do Dia")

        FinancialTipCard(
            tip = "Economize pequenas quantias diariamente. Guardar R$ 5 por dia resulta em R$ 1.825 por ano!",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun QuickActionButtons(
    onBudgetClick: () -> Unit,
    onGoalsClick: () -> Unit,
    onCoursesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        QuickActionButton(
            icon = Icons.Default.AccountBalance,
            label = "Orçamento",
            onClick = onBudgetClick
        )

        QuickActionButton(
            icon = Icons.Default.Star,
            label = "Metas",
            onClick = onGoalsClick
        )

        QuickActionButton(
            icon = Icons.Default.School,
            label = "Cursos",
            onClick = onCoursesClick
        )
    }
}

@Composable
fun QuickActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp)
    ) {
        FilledIconButton(
            onClick = onClick,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun FinancialTipCard(
    tip: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = tip,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Função utilitária para formatação de moeda
private fun formatCurrency(value: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return format.format(value)
}