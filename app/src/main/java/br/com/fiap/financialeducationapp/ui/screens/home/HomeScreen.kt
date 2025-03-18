package br.com.fiap.financialeducationapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.financialeducationapp.ui.components.SectionHeader
import br.com.fiap.financialeducationapp.ui.navigation.Screen
import br.com.fiap.financialeducationapp.ui.theme.IncomeGreen
import br.com.fiap.financialeducationapp.ui.theme.ExpenseRed

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Welcome section
        Text(
            text = "Bem-vindo ao FinEdu",
            style = MaterialTheme.typography.displayMedium,
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
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Receitas: R$ 2.500,00",
                    style = MaterialTheme.typography.bodyLarge,
                    color = IncomeGreen
                )

                Text(
                    text = "Despesas: R$ 1.800,00",
                    style = MaterialTheme.typography.bodyLarge,
                    color = ExpenseRed,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "Saldo: R$ 700,00",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
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
fun QuickActionButtons(
    onBudgetClick: () -> Unit,
    onGoalsClick: () -> Unit,
    onCoursesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Implementation will be added in the next part
}

@Composable
fun FinancialTipCard(
    tip: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = 16.dp),
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