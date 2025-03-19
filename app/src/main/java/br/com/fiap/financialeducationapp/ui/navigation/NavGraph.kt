package br.com.fiap.financialeducationapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.financialeducationapp.ui.components.BottomNavBar
import br.com.fiap.financialeducationapp.ui.screens.budget.BudgetScreen
import br.com.fiap.financialeducationapp.ui.screens.courses.CoursesScreen
import br.com.fiap.financialeducationapp.ui.screens.courses.CourseDetailScreen
import br.com.fiap.financialeducationapp.ui.screens.goals.GoalsScreen
import br.com.fiap.financialeducationapp.ui.screens.home.HomeScreen
import br.com.fiap.financialeducationapp.ui.screens.profile.ProfileScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            // Só mostrar a BottomNavBar nas telas principais
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            val mainRoutes = listOf(
                Screen.Home.route,
                Screen.Courses.route,
                Screen.Budget.route,
                Screen.Goals.route,
                Screen.Profile.route
            )

            if (currentRoute in mainRoutes) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }

            composable(Screen.Budget.route) {
                BudgetScreen()
            }

            composable(Screen.Goals.route) {
                GoalsScreen()
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }

            composable(Screen.Courses.route) {
                CoursesScreen(navController = navController)
            }

            composable(
                route = Screen.CourseDetail.route + "/{courseId}",
                arguments = listOf(
                    navArgument("courseId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
                CourseDetailScreen(
                    courseId = courseId,
                    navController = navController
                )
            }
        }
    }
}

