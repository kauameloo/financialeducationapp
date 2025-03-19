package br.com.fiap.financialeducationapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.financialeducationapp.ui.screens.budget.BudgetScreen
import br.com.fiap.financialeducationapp.ui.screens.courses.CoursesScreen
import br.com.fiap.financialeducationapp.ui.screens.courses.CourseDetailScreen
import br.com.fiap.financialeducationapp.ui.screens.goals.GoalsScreen
import br.com.fiap.financialeducationapp.ui.screens.home.HomeScreen
import br.com.fiap.financialeducationapp.ui.screens.profile.ProfileScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
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