package br.com.fiap.financialeducationapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Budget : Screen("budget")
    object Goals : Screen("goals")
    object Profile : Screen("profile")
    object Courses : Screen("courses")
    object CourseDetail : Screen("course_detail") {
        fun createRoute(courseId: String): String = "$route/$courseId"
    }
}