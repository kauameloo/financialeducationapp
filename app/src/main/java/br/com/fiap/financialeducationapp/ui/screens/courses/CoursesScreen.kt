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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.fiap.financialeducationapp.ui.components.CourseCard
import br.com.fiap.financialeducationapp.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(
    navController: NavController,
    viewModel: CoursesViewModel = hiltViewModel()
) {
    val courses by viewModel.courses.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cursos de Educação Financeira") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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