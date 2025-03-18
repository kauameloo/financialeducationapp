package br.com.fiap.financialeducationapp.ui.screens.courses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import br.com.fiap.financialeducationapp.data.repository.CourseRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    courseId: String,
    navController: NavController
) {
    // In a real app, this would come from a ViewModel
    val courseRepository = remember { CourseRepository() }
    val course = remember { courseRepository.getCourseById(courseId) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(course?.title ?: "Detalhes do Curso") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
            }
        )

        if (course != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(course.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = course.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = course.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Nível: ${course.difficulty}",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = course.description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Módulos",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    course.modules.forEachIndexed { index, module ->
                        ModuleCard(
                            moduleNumber = index + 1,
                            title = module.title,
                            description = module.content,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { /* Start course */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Iniciar Curso")
                    }
                }
            }
        } else {
            Text(
                text = "Curso não encontrado",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun ModuleCard(
    moduleNumber: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Módulo $moduleNumber: $title",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}