package br.com.fiap.financialeducationapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.fiap.financialeducationapp.ui.screens.budget.BudgetScreen
import br.com.fiap.financialeducationapp.ui.screens.courses.CourseDetailScreen
import br.com.fiap.financialeducationapp.ui.screens.courses.CoursesScreen
import br.com.fiap.financialeducationapp.ui.screens.goals.GoalsScreen
import br.com.fiap.financialeducationapp.ui.screens.home.HomeScreen
import br.com.fiap.financialeducationapp.ui.screens.profile.ProfileScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Courses : Screen("courses")
    object CourseDetail : Screen("course_detail/{courseId}") {
        fun createRoute(courseId: String) = "course_detail/$courseId"
    }
    object Budget : Screen("budget")
    object Goals : Screen("goals")
    object Profile : Screen("profile")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.Courses.route) {
            CoursesScreen(navController = navController)
        }

        composable(Screen.CourseDetail.route) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
            CourseDetailScreen(
                courseId = courseId,
                navController = navController
            )
        }

        composable(Screen.Budget.route) {
            BudgetScreen(navController = navController)
        }

        composable(Screen.Goals.route) {
            GoalsScreen(navController = navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}