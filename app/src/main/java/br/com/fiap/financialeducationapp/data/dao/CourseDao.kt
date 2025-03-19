package br.com.fiap.financialeducationapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import br.com.fiap.financialeducationapp.data.model.Course
import br.com.fiap.financialeducationapp.data.model.CourseProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses ORDER BY title ASC")
    fun getAllCourses(): Flow<List<Course>>

    @Query("SELECT * FROM courses WHERE id = :courseId")
    fun getCourseById(courseId: String): Flow<Course?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourseProgress(courseProgress: CourseProgress)

    @Query("SELECT * FROM course_progress WHERE userId = :userId AND courseId = :courseId")
    fun getCourseProgressForUser(userId: String, courseId: String): Flow<CourseProgress?>

    @Query("SELECT * FROM course_progress WHERE userId = :userId")
    fun getAllCourseProgressForUser(userId: String): Flow<List<CourseProgress>>

    @Transaction
    suspend fun updateCourseProgress(courseProgress: CourseProgress) {
        insertCourseProgress(courseProgress)
    }
}