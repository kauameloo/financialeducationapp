package br.com.fiap.financialeducationapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "course_progress",
    primaryKeys = ["userId", "courseId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Course::class,
            parentColumns = ["id"],
            childColumns = ["courseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["courseId"])
    ]
)
data class CourseProgress(
    val userId: String,
    val courseId: String,
    val completedModules: List<String>,
    val lastAccessTimestamp: Long,
    val isCompleted: Boolean
)