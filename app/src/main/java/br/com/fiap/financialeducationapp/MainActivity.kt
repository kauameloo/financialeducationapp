package br.com.fiap.financialeducationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import br.com.fiap.financialeducationapp.ui.components.BottomNavBar
import br.com.fiap.financialeducationapp.ui.navigation.NavGraph
import br.com.fiap.financialeducationapp.ui.theme.FinancialEducationTheme
import br.com.fiap.financialeducationapp.ui.theme.Surface

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinancialEducationTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { BottomNavBar(navController = navController) }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    // This is a placeholder - we'll implement the full navigation later
    Text(
        text = "Financial Education App",
        modifier = Modifier.padding(16.dp)
    )
}

// If you need a preview, make sure it's a @Composable function
@Composable
fun MainScreenPreview() {
    FinancialEducationTheme {
        MainScreen()
    }
}