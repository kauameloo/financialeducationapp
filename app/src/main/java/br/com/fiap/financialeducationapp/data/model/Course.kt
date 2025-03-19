package br.com.fiap.financialeducationapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val durationMinutes: Int,
    val level: String, // Alterado de "difficulty" para "level"
    val modules: List<String>
)