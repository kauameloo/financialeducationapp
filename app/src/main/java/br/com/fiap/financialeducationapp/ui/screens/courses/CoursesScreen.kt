package br.com.fiap.financialeducationapp.ui.screens.courses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.financialeducationapp.data.model.Course
import br.com.fiap.financialeducationapp.data.repository.CourseRepository
import br.com.fiap.financialeducationapp.ui.components.CourseCard
import br.com.fiap.financialeducationapp.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(navController: NavController) {
    // In a real app, this would come from a ViewModel
    val courseRepository = remember { CourseRepository() }
    val courses = remember { courseRepository.getCourses() }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Cursos de Educação Financeira") }
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            items(courses) { course ->
                CourseCard(
                    course = course,
                    onClick = {
                        navController.navigate(Screen.CourseDetail.createRoute(course.id))
                    }
                )
            }
        }
    }
}