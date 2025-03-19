package br.com.fiap.financialeducationapp.ui.screens.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.financialeducationapp.data.model.Course
import br.com.fiap.financialeducationapp.data.repository.CoursesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository
) : ViewModel() {

    private val _course = MutableStateFlow<Course?>(null)
    val course: StateFlow<Course?> = _course

    fun loadCourse(courseId: String) {
        viewModelScope.launch {
            coursesRepository.getCourseById(courseId)
                .collect { course ->
                    _course.value = course
                }
        }
    }
}